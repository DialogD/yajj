package com.dialogd.yajj.mapper;

import com.dialogd.yajj.dao.IBaseDao;
import com.dialogd.yajj.entity.ProductType;

import java.util.List;

public interface ProductTypeMapper extends IBaseDao<ProductType> {

    List<Integer> selectByType(int type);
}