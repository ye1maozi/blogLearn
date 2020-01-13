package com.learn.blog.pojo;

import com.learn.blog.util.BaseVO;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

public class Article extends BaseVO {

    private Integer userid;

    private Integer categoryid;


    private String title;

    private String author;

    private String content;

    private String contentmd;

    private Byte ismarkdown;

    private String topimg;

    private Date createtime;

    private Date updatetime;

    @Transient
    private List<Integer> tags;

    @Transient
    private String categoryname;
    @Transient
    private List<Tags> tagsList;

    @Transient
    private  Category category;


    @Transient
    private  Integer lookCount = 0;


    @Transient
    private  Integer commentCount = 0;

    public Integer getLookCount() {
        return lookCount;
    }

    public void setLookCount(Integer lookCount) {
        this.lookCount = lookCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentmd() {
        return contentmd;
    }

    public void setContentmd(String contentmd) {
        this.contentmd = contentmd;
    }

    public Byte getIsmarkdown() {
        return ismarkdown;
    }

    public void setIsmarkdown(Byte ismarkdown) {
        this.ismarkdown = ismarkdown;
    }

    public String getTopimg() {
        return topimg;
    }

    public void setTopimg(String topimg) {
        this.topimg = topimg;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public List<Integer> getTags() {
        return tags;
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public List<Tags> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<Tags> tagsList) {
        this.tagsList = tagsList;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}