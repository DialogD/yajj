package com.dialogd.yajj.service.impl;

import com.dialogd.yajj.dao.IBaseDao;
import com.dialogd.yajj.entity.OrderDetail;
import com.dialogd.yajj.mapper.OrderDetailMapper;
import com.dialogd.yajj.service.IOrderDetailSevice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderDetailSeviceImpl extends BaseServiceImpl<OrderDetail> implements IOrderDetailSevice {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public IBaseDao getDao() {
        return orderDetailMapper;
    }


    @Override
    public List<OrderDetail> selectByOrderNo(Long orderNo) {
        List<OrderDetail> detailList = orderDetailMapper.selectByOrderNo(orderNo);
        return detailList;
    }
}
