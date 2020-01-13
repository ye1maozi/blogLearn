package com.learn.blog.pojo;

import com.learn.blog.util.BaseVO;

public class Tags extends BaseVO {

    private String name;

    private String desction;

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
}