package com.learn.blog.service;

import com.learn.blog.pojo.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService extends BaseService<Comment> {
    List<Comment> selectCommentsByCateogryId(int id);

    List<Comment> queryPage(Map<String, Object> params);

    List<Comment> selectNewList();
    Integer getCount(Map<String, Object> params);

    Integer changeStatus(Comment comment);

    int Count();
}
