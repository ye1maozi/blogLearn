package com.learn.blog.dao;

import com.learn.blog.pojo.Article;
import com.learn.blog.util.ArticleArchivesVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper   extends BaseMapper<Article> {
    List<Article> selectByTime(@Param("limit") int i);

    List<Article> queryPage(@Param("start") Integer start,
                            @Param("end") Integer end,
                            @Param("sortby") String sortby,
                            @Param("sort") String sort,
                            @Param("categoryId") Integer categoryId,
                            @Param("tagId") Integer tagId,
                            @Param("year") Integer year,
                            @Param("month") Integer month);

    List<ArticleArchivesVo> queyArticleArchives();
}