package com.learn.blog.pojo;

import com.learn.blog.util.BaseVO;
import com.learn.blog.util.BaseVOForChild;

import java.util.Date;
import java.util.List;

public class Role extends BaseVOForChild<Role> {

    private String name;


    private Date createtime;

    private String desction;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getDesction() {
        return desction;
    }

    public void setDesction(String desction) {
        this.desction = desction;
    }

}