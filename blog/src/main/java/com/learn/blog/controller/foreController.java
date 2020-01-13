package com.learn.blog.controller;

import com.learn.blog.pojo.Article;
import com.learn.blog.pojo.Comment;
import com.learn.blog.pojo.Tags;
import com.learn.blog.pojo.User;
import com.learn.blog.service.ArticleService;
import com.learn.blog.service.CommentService;
import com.learn.blog.service.TagsService;
import com.learn.blog.service.UserService;
import com.learn.blog.util.HomeVO;
import com.learn.blog.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/fore/")
public class foreController {

    @Autowired
    UserService userService;

    @Autowired
    TagsService tagsService;
    @Autowired
    ArticleService articleService;

    @Autowired
    CommentService commentService;
    @PostMapping("login")
    public Object userLogin(@RequestBody User user , HttpSession session){

        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()){
            subject.logout();
        }

        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);
        String password = user.getPassword();
        System.out.printf("login %s %s \n" ,name ,password);

        UsernamePasswordToken token = new UsernamePasswordToken(name,password);
        try{
            subject.login(token);
        }catch (AuthenticationException e){
//            e.printStackTrace();
            return Result.fail("用户名或密码错误");
        }
        user.setLastlogintime(new Date());
        return Result.success(user);
    }

    @PostMapping("register")
    public Object userRegister(@RequestBody User user , HttpSession session){
        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);
        User u = userService.selectByUsername(name);
        if (null != u ){
            return Result.fail("用户名已存在");
        }

        String password = user.getPassword();
        System.out.printf("register %s %s\n" ,name ,password);
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String algorithnames = "md5";
        String encodedPassword = new SimpleHash(algorithnames,password,salt,times).toString();

        user.setName(name);
        user.setSalt(salt);
        user.setPassword(encodedPassword);

        user.setCreatetime(new Date());
        user.setLastlogintime(new Date());
        userService.insert(user);
        return Result.success(user);
    }

    @GetMapping("/userhome")
    public Object getUserHome(){
        List<Tags> tags = tagsService.selectAll();
        List<Article> articles = articleService.selectNewList();
        List<Comment> comments = commentService.selectNewList();
        HomeVO homeVO = new HomeVO();
        homeVO.setArticles(articles);
        homeVO.setComments(comments);
        homeVO.setTagsList(tags);
        return Result.success(homeVO);
    }

}
