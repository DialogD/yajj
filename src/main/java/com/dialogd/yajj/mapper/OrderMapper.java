package com.dialogd.yajj.mapper;

import com.dialogd.yajj.dao.IBaseDao;
import com.dialogd.yajj.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderMapper extends IBaseDao<Order> {

    List<Order> selectPage(@Param("userId") Long userId, @Param("orderStatus") Integer orderStatus);

    List<Order> getConditionPage(Map<String, Object> param);
}