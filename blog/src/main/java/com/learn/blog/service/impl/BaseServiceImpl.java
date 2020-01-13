package com.learn.blog.service.impl;

import com.learn.blog.dao.BaseMapper;
import com.learn.blog.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BaseServiceImpl<T> implements BaseService<T> {
    @Autowired
    BaseMapper<T> mapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(T entity) {
        return mapper.insert(entity);
    }

    @Override
    public int insertSelective(T entity) {
        return mapper.insertSelective(entity);
    }

    @Override
    public T selectByPrimaryKey(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int updateByPrimaryKey(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    public List<T> select(T entity) {
        return null;
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }
}
