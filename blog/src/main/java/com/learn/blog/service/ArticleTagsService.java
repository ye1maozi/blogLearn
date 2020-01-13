package com.learn.blog.service;

import com.learn.blog.pojo.ArticleTags;

import java.util.List;

public interface ArticleTagsService extends BaseService<ArticleTags> {
    void updateArticle2Tags(Integer aid, List<Integer> list);
}
