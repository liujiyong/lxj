package com.lxj.service;

import java.util.List;

import com.lxj.model.News;
import com.lxj.response.BasicResponse;

public interface NewsService {

    public BasicResponse<List<News>> getAllNews();

    public BasicResponse<String> createNews(News news, String userId);

    public BasicResponse<String> updateNews(News news);

    public BasicResponse<String> deleteNews(String newsId);
}
