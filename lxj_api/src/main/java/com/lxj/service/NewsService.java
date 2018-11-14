package com.lxj.service;

import com.lxj.response.BasicResponse;

public interface NewsService {

    public BasicResponse getAllNews();

    public BasicResponse createNews();

    public BasicResponse updateNews();

    public BasicResponse deleteNews();
}
