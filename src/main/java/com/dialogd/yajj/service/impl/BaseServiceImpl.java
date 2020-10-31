package com.dialogd.yajj.service.impl;

import com.dialogd.yajj.dao.IBaseDao;
import com.dialogd.yajj.service.IBaseService;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseServiceImpl<T> implements IBaseService<T> {

    public abstract IBaseDao<T> getDao();

    @Override
    public int deleteByPrimaryKey(Long id) {
        return getDao().deleteByPrimaryKey(id);
    }

    @Override
    public int insert(T t) {
        return getDao().insert(t);
    }

    @Override
    public int insertSelective(T t) {
        return getDao().insertSelective(t);
    }

    @Override
    public T selectByPrimaryKey(Long id) {
        return getDao().selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(T t) {
        return getDao().updateByPrimaryKeySelective(t);
    }

    @Override
    public int updateByPrimaryKey(T t) {
        return getDao().updateByPrimaryKey(t);
    }

}
