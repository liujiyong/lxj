package com.lxj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lxj.response.BasicResponse;
import com.lxj.service.NewsService;

@RestController
@RequestMapping(value = "api/v2/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    /**
     * 所有公告列表
     * 
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public BasicResponse getNewsList() {
        return newsService.getAllNews();
    }

    /**
     * 创建公告
     * 
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public BasicResponse createNews() {
        return newsService.createNews();
    }

    /**
     * 更新公告
     * 
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public BasicResponse updateNews() {
        return newsService.updateNews();
    }

    /**
     * 删除公告
     * 
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public BasicResponse deleteNews() {
        return newsService.deleteNews();
    }
}
