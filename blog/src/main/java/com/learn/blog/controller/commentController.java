package com.learn.blog.controller;

import com.learn.blog.pojo.Article;
import com.learn.blog.pojo.Comment;
import com.learn.blog.service.CommentService;
import com.learn.blog.util.IPUtil;
import com.learn.blog.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/api/fore/comments","/api/comments"})
public class commentController {

    @Autowired
    CommentService commentService;
    @GetMapping("/{id}")
    public Object getCommentsById(@PathVariable("id") int id) {
        List<Comment> comments = commentService.selectCommentsByCateogryId(id);

        return Result.success(comments);
    }
    @GetMapping("")
    public Object getComments(@RequestParam Map<String, Object> params){
        List<Comment> articles = commentService.queryPage(params);
        return Result.success(articles);
    }
    @PostMapping("")
    public Object postComments(@RequestBody Comment comment, HttpServletRequest request){
        String ip = IPUtil.getUserIp(request);
        comment.setIp(ip);
        comment.setStatus(0);
        comment.setCreatetime(new Date());
        comment.setUpdatetime(new Date());
        commentService.insertSelective(comment);
        return Result.success(comment);
    }

    @PutMapping("")
    public Object changeStatus(@RequestBody Comment comment){
        Integer status = commentService.changeStatus(comment);
        if (-1 == status){
            return Result.fail("error update");
        }
        return Result.success(status);
    }


    @GetMapping("/total")
    public Object getTotal(@RequestParam Map<String, Object> params){
       Integer toal = commentService.getCount(params);
        return Result.success(toal);
    }

}
