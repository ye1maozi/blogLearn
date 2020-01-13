package com.learn.blog.pojo;

import com.learn.blog.util.BaseVO;

public class ArticleTags extends BaseVO {

    private Integer articleid;

    private Integer tagid;

    public Integer getArticleid() {
        return articleid;
    }

    public void setArticleid(Integer articleid) {
        this.articleid = articleid;
    }

    public Integer getTagid() {
        return tagid;
    }

    public void setTagid(Integer tagid) {
        this.tagid = tagid;
    }
}