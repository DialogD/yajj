package com.dialogd.yajj.mapper;

import com.dialogd.yajj.dao.IBaseDao;
import com.dialogd.yajj.entity.PayType;

public interface PayTypeMapper extends IBaseDao<PayType> {
    int deleteByPrimaryKey(Integer id);

}