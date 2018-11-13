package com.lxj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lxj.BasicResponse;
import com.lxj.mapper.NewsMapper;
import com.lxj.model.News;
import com.lxj.service.NewsService;

@Service("newsService")
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public BasicResponse getAllNews() {
        List<News> news = newsMapper.getAllNews();
        return new BasicResponse(200, "", news);
    }

	@Override
	public BasicResponse createNews() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasicResponse updateNews() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasicResponse deleteNews() {
		// TODO Auto-generated method stub
		return null;
	}

}
