package com.learn.blog.service;

import java.util.List;

public interface BaseService<T> {
    int deleteByPrimaryKey(Integer id);

    int insert(T entity);

    int insertSelective(T entity);

    T selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(T entity);

    int updateByPrimaryKey(T entity);

    List<T> select(T entity);

    List<T> selectAll();
}
