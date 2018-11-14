package com.lxj.mapper;

import java.util.List;

import com.lxj.model.News;

public interface NewsMapper {

    public List<News> getAllNews();

    public int createNews(News news);
}
