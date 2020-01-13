package com.learn.blog.dao;

import com.learn.blog.pojo.ArticleLook;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface ArticleLookMapper extends BaseMapper<ArticleLook> {

    int checkArticleLook(
            @Param("articleId") Integer articleId,
            @Param("ip") String ip,
            @Param("date") Date date);
}