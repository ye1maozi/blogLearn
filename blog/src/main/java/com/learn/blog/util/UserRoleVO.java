package com.learn.blog.util;

import com.learn.blog.pojo.UserRole;

import java.util.List;

public class UserRoleVO {

    private Integer id;
    private List<UserRole> changeList;

    public List<UserRole> getChangeList() {
        return changeList;
    }

    public void setChangeList(List<UserRole> changeList) {
        this.changeList = changeList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
