package com.learn.blog.service;

import com.learn.blog.pojo.Article;
import com.learn.blog.util.ArticleArchivesVo;

import java.util.List;
import java.util.Map;

public interface ArticleService extends BaseService<Article> {
    List<Article> selectNewList();

    List<Article> queryPage(Map<String, Object> params);

    List<ArticleArchivesVo> queyArticleArchives();
}
