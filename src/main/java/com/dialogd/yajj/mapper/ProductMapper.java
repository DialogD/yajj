package com.dialogd.yajj.mapper;

import com.dialogd.yajj.dao.IBaseDao;
import com.dialogd.yajj.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProductMapper extends IBaseDao<Product> {

    List<Product> queryByType(@Param("typeIds") List<Integer> typeIds);

    List<Product> list();

    List<Product> getConditionPage(Map<String, Object> param);

    void batchDelProduct(@Param("ids") List<Integer> ids);

    int isExistName(String name);
}