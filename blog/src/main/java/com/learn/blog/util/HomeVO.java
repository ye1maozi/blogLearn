package com.learn.blog.util;

import com.learn.blog.pojo.Article;
import com.learn.blog.pojo.Comment;
import com.learn.blog.pojo.Tags;
import com.learn.blog.util.Stage.BaseEntity;

import java.util.List;

public class HomeVO extends BaseEntity {

    private List<Article> articles;
    private List<Tags> tagsList;
    private List<Comment> comments;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<Tags> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<Tags> tagsList) {
        this.tagsList = tagsList;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
