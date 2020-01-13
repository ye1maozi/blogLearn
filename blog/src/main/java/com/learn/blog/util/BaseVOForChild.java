package com.learn.blog.util;

import java.util.List;

public class BaseVOForChild<T> extends BaseVO {

    private Integer pid;
    private List<T> children;
    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
