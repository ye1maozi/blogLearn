package com.learn.blog.pojo;

import com.learn.blog.util.BaseVO;

public class RolePermission extends BaseVO {

    private Integer permissionid;

    private Integer groupid;

    private Boolean newFlag;

    public Integer getPermissionid() {
        return permissionid;
    }

    public void setPermissionid(Integer permissionid) {
        this.permissionid = permissionid;
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