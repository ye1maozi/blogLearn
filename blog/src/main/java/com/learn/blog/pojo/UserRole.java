package com.learn.blog.pojo;

import com.learn.blog.util.BaseVO;

public class UserRole extends BaseVO {

    private Integer userid;

    private Integer groupid;

    private Boolean newFlag;
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public Boolean getNewFlag() {
        return newFlag;
    }

    public void setNewFlag(Boolean newFlag) {
        this.newFlag = newFlag;
    }
}