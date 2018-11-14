package com.lxj.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lxj.model.News;
import com.lxj.response.BasicResponse;
import com.lxj.service.NewsService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "api/v1/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    /**
     * 获取公告列表
     * 
     * @return
     */
    @ApiOperation(value = "获取公告列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public BasicResponse<List<News>> getNewsList() {
        return newsService.getAllNews();
    }

    /**
     * 创建公告
     * 
     * @return
     */
    @ApiOperation(value = "创建公告")
    @ApiImplicitParam(name = "news", value = "公告对象,传入json格式", paramType = "body", required = true, dataType = "string")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public BasicResponse<String> createNews(@RequestBody News news) {
        String userId = SecurityUtils.getSubject().getPrincipal().toString();
        return newsService.createNews(news, userId);
    }

    /**
     * 更新公告
     * 
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public BasicResponse<String> updateNews(@RequestBody News news) {
        return newsService.updateNews(news);
    }

    /**
     * 删除公告
     * 
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public BasicResponse<String> deleteNews(@PathVariable("newsId") String newsId) {
        return newsService.deleteNews(newsId);
    }
}
