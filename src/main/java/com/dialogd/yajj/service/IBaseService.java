package com.dialogd.yajj.service;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 公共接口方法
 */
public interface IBaseService<T> {

    int deleteByPrimaryKey(Long id);

    int insert(T t);

    int insertSelective(T t);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T t);

    int updateByPrimaryKey(T t);

}
