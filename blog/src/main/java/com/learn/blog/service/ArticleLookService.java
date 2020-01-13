package com.learn.blog.service;

import com.learn.blog.pojo.ArticleLook;

import javax.servlet.http.HttpServletRequest;

public interface ArticleLookService extends BaseService<ArticleLook> {
    boolean checkLook(HttpServletRequest request, int id);
}
