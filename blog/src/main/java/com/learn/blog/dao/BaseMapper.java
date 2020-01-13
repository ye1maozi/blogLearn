package com.learn.blog.dao;

import java.util.List;

/**
 * 基础mapper curd
 * @param <T>
 */
public interface BaseMapper<T> {
    int deleteByPrimaryKey(Integer id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);

    List<T> selectAll();
}
