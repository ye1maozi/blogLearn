package com.learn.blog.service.impl;

import com.learn.blog.dao.CommentMapper;
import com.learn.blog.pojo.Comment;
import com.learn.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements CommentService {

    @Autowired
    CommentMapper commentMapper;
    @Override
    public List<Comment> selectCommentsByCateogryId(int id) {
        return commentMapper.selectCommentsByCateogryId(id,0);
    }
    @Override
    public List<Comment> selectNewList() {

        return    commentMapper.selectByTime(5);
    }

    @Override
    public List<Comment> queryPage(Map<String, Object> params) {
        int start = Integer.parseInt( params.get("pageNo").toString());
        if (params.get("pageNo") == null){
            return commentMapper.selectAll();
        }
        int pageSize = Integer.parseInt( params.get("pageSize").toString());
        start = (start - 1) * pageSize;
        Integer end = start + pageSize;
//        String sortby = (String) params.get("six");
//        String sort = (String) params.get("sort");
        Integer categoryId = null;
        if (params.containsKey("articleId") ){
            categoryId = Integer.parseInt( params.get("articleId").toString());
        }
        Integer status = null;
        if (params.containsKey("status") ){
            status = Integer.parseInt( params.get("status").toString());
        }
        if (status != null) {
            return commentMapper.queryPageMore(start,end,categoryId,status);
        }else {
            return commentMapper.queryPage(start,end,categoryId,0);
        }

    }

    @Override
    public Integer getCount(Map<String, Object> params) {
        Integer status = null;
        if (!params.isEmpty()) {
            if (params.containsKey("status")) {
                status = Integer.parseInt( params.get("status").toString());
            }
        }
        return    commentMapper.getCount(status);
    }

    @Override
    public Integer changeStatus(Comment comment) {
        Integer id = comment.getId();
        Integer status = comment.getStatus();
        if (status == null || id == null) {
            return -1;
        }
        commentMapper.changeStatus(status,id);
        return status;
    }

    @Override
    public int Count() {
        return commentMapper.Count();
    }
}
