package com.learn.blog.dao;

import com.learn.blog.pojo.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper extends BaseMapper<Comment>{

    List<Comment> selectCommentsByCateogryId(int id,int status);
    List<Comment> queryPage(@Param("start") Integer start,
                            @Param("end")Integer end,
                            @Param("articleId") Integer articleId,
                            @Param("status")Integer status
    );
    List<Comment> queryPageMore(@Param("start") Integer start,
                            @Param("end")Integer end,
                            @Param("articleId") Integer articleId,
                            @Param("status")Integer status
    );
    List<Comment> selectByTime(int i);

    Integer getCount( @Param("status")Integer status);

    void changeStatus( @Param("status") Integer status, @Param("id") Integer id);

    int Count();
}