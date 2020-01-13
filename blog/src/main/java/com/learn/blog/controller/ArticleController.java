package com.learn.blog.controller;

import com.learn.blog.pojo.Article;
import com.learn.blog.pojo.ArticleTags;
import com.learn.blog.pojo.User;
import com.learn.blog.service.ArticleLookService;
import com.learn.blog.service.ArticleService;
import com.learn.blog.service.ArticleTagsService;
import com.learn.blog.service.UserService;
import com.learn.blog.util.ArticleArchivesVo;
import com.learn.blog.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/api/articles","/api/fore/articles"})
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;
    @Autowired
    ArticleTagsService articleTagsService;

    @Autowired
    ArticleLookService articleLookService;

    @PostMapping("")
    public Object addArticle(@RequestBody Article article){
        // 当前的角色
        Subject subject = SecurityUtils.getSubject();
        String userName;
        if (subject.isAuthenticated()){
            userName = subject.getPrincipal().toString();
        } else {
            userName = "admin";
        }
        User user = userService.selectByUsername(userName);

        article.setUserid(user.getId());
        article.setAuthor(userName);
        article.setCreatetime(new Date());
        article.setUpdatetime(new Date());

        List<Integer> list = article.getTags();
         articleService.insertSelective(article);
        Integer aid = article.getId();
        // tag - article
        for (Integer tagId: list
             ) {
            ArticleTags at = new ArticleTags();
            at.setArticleid(aid);
            at.setTagid(tagId);
            articleTagsService.insert(at);
        }
        return Result.success(article);
    }


    @PutMapping("")
    public Object updateArticle(@RequestBody Article article){
        article.setUpdatetime(new Date());

        List<Integer> list = article.getTags();
        articleService.updateByPrimaryKeySelective(article);

        Integer aid = article.getId();
        // tag - article
         articleTagsService.updateArticle2Tags(aid,list);

        return Result.success(article);
    }
//    @GetMapping("")
//    public Object getArticleList(){
//
//        List<Article> list = articleService.selectAll();
//
//        return Result.success(list);
//    }
    @GetMapping("")
    public Object getArticles(@RequestParam Map<String, Object> params){
        List<Article> articles = articleService.queryPage(params);
        return Result.success(articles);
    }
    @GetMapping("{id}")
    public Object getArticleById(@PathVariable("id") int id, HttpServletRequest request){

       Article article = articleService.selectByPrimaryKey(id);
        boolean newLook = articleLookService.checkLook(request,id);
        if (newLook){
            article.setLookCount(article.getLookCount() + 1);
        }

        return Result.success(article);
    }

    @GetMapping("history")
    public Object getHistory(){
        List<ArticleArchivesVo> list = articleService.queyArticleArchives();
        return Result.success(list);
    }
}
