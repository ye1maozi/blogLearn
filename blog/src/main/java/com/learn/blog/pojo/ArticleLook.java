package com.learn.blog.pojo;

import com.learn.blog.util.BaseVO;

import java.util.Date;

public class ArticleLook  extends BaseVO {

    private Integer userid;

    private Integer articleid;

    private Date createtime;

    private String ip;
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getArticleid() {
        return articleid;
    }

    public void setArticleid(Integer articleid) {
        this.articleid = articleid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}