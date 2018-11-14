package com.lxj.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lxj.LxjPropertyService;
import com.lxj.constants.CommonConstants;
import com.lxj.response.BasicResponse;
import com.lxj.util.IDGenerator;

@RestController
public class CommonController {

    private static final Logger LOGGER = LogManager.getLogger(CommonController.class);

    @Autowired
    private LxjPropertyService kuaiyiPropertyService;

    /**
     * 
     * @return
     */
    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BasicResponse unauthorized() {
        LOGGER.info("run unauthorized()");
        return new BasicResponse(401, "Unauthorized", null);
    }

    /**
     * 
     * @param file
     * @param fileType
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public BasicResponse fileUpload(@RequestParam("file") MultipartFile file,
            @RequestParam("fileType") String fileType) {
        if (file.isEmpty()) {
            return new BasicResponse(200, "请选择文件", null);
        }

        if (StringUtils.isEmpty(fileType)) {
            return new BasicResponse(200, "请指定文件类型，如(doc,img)", null);
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        LOGGER.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        LOGGER.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String rootPath = kuaiyiPropertyService.getValue(CommonConstants.DOC_ROOR_PATH);
        LOGGER.info("Path：" + rootPath);
        rootPath = rootPath + File.separator + fileType + File.separator;
        // 解决中文问题，liunx下中文路径，图片显示问题
        String fileUuid = IDGenerator.generate();
        fileName = fileUuid + suffixName;
        File dest = new File(rootPath + fileName);
        LOGGER.info("Path：" + dest.getParentFile());
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return new BasicResponse(200, "上传成功", fileUuid);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BasicResponse(200, "上传失败", null);
    }

    /**
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/batch/upload", method = RequestMethod.POST)
    @ResponseBody
    public BasicResponse handleFileUpload(HttpServletRequest request, @RequestParam("fileType") String fileType) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        String[] fileUuids = new String[files.size()];
        for (int i = 0; i < files.size(); ++i) {
            BasicResponse basicResponse = fileUpload(files.get(i), fileType);
            if (basicResponse.getData() != null) {
                fileUuids[i] = basicResponse.getData().toString();
            }

        }
        return new BasicResponse(200, "全部上传成功.", fileUuids);
    }

    /**
     * 
     * @param request
     * @param response
     * @param fileType
     * @param fileUuid
     * @return
     */
    @RequestMapping(value = "/download")
    @ResponseBody
    public BasicResponse downloadFile(org.apache.catalina.servlet4preview.http.HttpServletRequest request,
            HttpServletResponse response, @RequestParam("fileType") String fileType,
            @RequestParam("fileUuid") String fileUuid) {

        if (StringUtils.isEmpty(fileUuid)) {
            return new BasicResponse(200, "fileUuid not is null.", null);
        }
        if (StringUtils.isEmpty(fileType)) {
            return new BasicResponse(200, "fileType not is null.", null);
        }

        String rootPath = kuaiyiPropertyService.getValue(CommonConstants.DOC_ROOR_PATH);

        rootPath = rootPath + File.separator + fileType + File.separator;

        String fileName = loadFileByFileUuid(new File(rootPath), fileUuid);

        if (StringUtils.isEmpty(fileName)) {
            LOGGER.info("Not found this file by uuid：" + fileUuid);
            return new BasicResponse(200, "Not found this file by uuid：" + fileUuid, null);
        }
        File file = new File(fileName);

        if (!file.exists()) {
            return new BasicResponse(200, "该文件不存在：" + fileName, null);
        }

        response.setContentType("application/force-download");// 设置强制下载不打开
        fileName = "downLoad.xml";
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }

            return new BasicResponse(200, "download success.", null);
        } catch (Exception e) {
            e.printStackTrace();

            return new BasicResponse(200, e.getMessage(), null);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return new BasicResponse(200, e.getMessage(), null);
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return new BasicResponse(200, e.getMessage(), null);
                }
            }
        }

    }

    /**
     *
     * @param loadPath
     * @param fileUuid
     * @return
     */
    private String loadFileByFileUuid(File loadPath, String fileUuid) {
        String loadFile = null;

        File[] fs = loadPath.listFiles();
        for (File f : fs) {
            if (f.isDirectory()) {
                loadFileByFileUuid(f, fileUuid);
            } else if (f.getName().contains(fileUuid)) {
                loadFile = f.getAbsolutePath();
                return loadFile;
            }
        }
        return loadFile;
    }
}
