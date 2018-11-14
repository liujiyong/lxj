package com.lxj.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lxj.LxjPropertyService;
import com.lxj.constants.Messages;
import com.lxj.mapper.NewsMapper;
import com.lxj.model.News;
import com.lxj.response.BasicResponse;
import com.lxj.service.NewsService;
import com.lxj.util.IDGenerator;

@Service("newsService")
public class NewsServiceImpl implements NewsService {
	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private NewsMapper newsMapper;

	@Autowired
	private LxjPropertyService lxjProperty;

	@Override
	public BasicResponse<List<News>> getAllNews() {
		List<News> news = newsMapper.getAllNews();
		return new BasicResponse<List<News>>(200, "get news success", news);
	}

	@Transactional
	public BasicResponse<String> createNews(News news, String userId) {
		if (StringUtils.isEmpty(news.getTitle())) {
			LOGGER.info("公告标题不能为空。");
			return new BasicResponse<String>(200, "create fail",
					String.format(lxjProperty.getValue(Messages.ERR10001), "公告标题"));
		}
		news.setId(IDGenerator.generate());
		news.setCreateUser(userId);
		news.setCreateTime(new Date());
		news.setUpdateUser(userId);
		news.setUpdateTime(new Date());
		int count = newsMapper.createNews(news);
		if (count > 0) {
			return new BasicResponse<String>(200, "create success", lxjProperty.getValue(Messages.MSG99999));
		} else {
			LOGGER.info("创建公告失败。");
			return new BasicResponse<String>(200, "create fail", lxjProperty.getValue(Messages.ERR99999));
		}
	}

	@Override
	public BasicResponse<String> updateNews(News news) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasicResponse<String> deleteNews(String newsId) {
		// TODO Auto-generated method stub
		return null;
	}

}
