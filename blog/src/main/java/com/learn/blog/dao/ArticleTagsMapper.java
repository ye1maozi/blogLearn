package com.learn.blog.dao;

import com.learn.blog.pojo.ArticleTags;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleTagsMapper extends BaseMapper<ArticleTags>{

    List<ArticleTags> selectByArticleId(@Param("articleid") Integer aid);

    void deleteByArticleAndTagId(@Param("articleid") Integer aid, @Param("tagid") Integer tid);
}