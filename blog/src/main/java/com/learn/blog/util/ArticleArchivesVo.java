package com.learn.blog.util;

import com.learn.blog.util.Stage.BaseEntity;

public class ArticleArchivesVo extends BaseEntity {
    private String year;
    private String month;
    private Integer count;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
