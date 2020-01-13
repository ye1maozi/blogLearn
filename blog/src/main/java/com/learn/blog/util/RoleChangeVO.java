package com.learn.blog.util;

import com.learn.blog.pojo.RolePermission;

import java.util.List;

public class RoleChangeVO {
    private Integer id;
    private List<RolePermission> changeList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<RolePermission> getChangeList() {
        return changeList;
    }

    public void setChangeList(List<RolePermission> changeList) {
        this.changeList = changeList;
    }
}
