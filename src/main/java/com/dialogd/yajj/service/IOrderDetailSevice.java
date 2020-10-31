package com.dialogd.yajj.service;

import com.dialogd.yajj.entity.OrderDetail;

import java.util.List;

public interface IOrderDetailSevice extends IBaseService<OrderDetail> {

    List<OrderDetail> selectByOrderNo(Long orderNo);
}
