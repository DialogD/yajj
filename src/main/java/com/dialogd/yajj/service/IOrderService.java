package com.dialogd.yajj.service;

import com.dialogd.yajj.common.Page;
import com.dialogd.yajj.entity.Order;
import com.dialogd.yajj.entity.User;
import com.github.pagehelper.PageInfo;

/**
 * @Date: 2020/4/18
 */
public interface IOrderService extends IBaseService<Order> {

    Integer submitOrder(Order order, User loginUser);

    PageInfo<Order> selectPage(Page page, Long id, Integer status);

    PageInfo getConditionPage(String orderNo, Byte orderStatus, Page page);
}
