package com.learn.blog.pojo;

import com.learn.blog.util.BaseVOForChild;


public class Permission extends BaseVOForChild<Permission> {

    private String name;

    private String desction;


    private Integer type;

    private String path;

    private String icon;

    private Integer status;

    private String component;

    private String apiurl = "";
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesction() {
        return desction;
    }

    public void setDesction(String desction) {
        this.desction = desction;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getApiurl() {
        return apiurl;
    }

    public void setApiurl(String apiurl) {
        this.apiurl = apiurl;
    }
}