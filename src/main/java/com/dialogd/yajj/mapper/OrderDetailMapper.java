package com.dialogd.yajj.mapper;

import com.dialogd.yajj.dao.IBaseDao;
import com.dialogd.yajj.entity.OrderDetail;

import java.util.List;

public interface OrderDetailMapper extends IBaseDao<OrderDetail> {

    List<OrderDetail> selectByOrderNo(Long orderNo);
}