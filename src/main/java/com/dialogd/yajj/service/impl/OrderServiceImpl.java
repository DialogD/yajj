package com.dialogd.yajj.service.impl;

import com.dialogd.yajj.common.Page;
import com.dialogd.yajj.dao.IBaseDao;
import com.dialogd.yajj.entity.*;
import com.dialogd.yajj.mapper.*;
import com.dialogd.yajj.service.IOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Date: 2020/4/18
 */
@Service
@Slf4j
public class OrderServiceImpl extends BaseServiceImpl<Order> implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private LogisticsProvidersMapper logisticsProvidersMapper;

    @Autowired
    private PayTypeMapper payTypeMapper;

    @Override
    public IBaseDao<Order> getDao() {
        return orderMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer submitOrder(Order order, User loginUser) {
        //1.添加订单表，明细表
        String allCartId = order.getOrderNo();
        List<String> cartIdList = Arrays.asList(allCartId.split("_"));
        //生成唯一的订单号orderNo
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmssSSSS");
        StringBuilder orderNo = new StringBuilder(format.format(System.currentTimeMillis())).append(loginUser.getId());
        order.setOrderNo(orderNo.toString());
        order.setUserId(loginUser.getId());
        order.setTotalMoney(order.getTotalMoney()*100);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        //插入订单表t_order
        int insertOrderResult = orderMapper.insertSelective(order);
        try {
            int detailResult = 0;
            if (insertOrderResult > 0){
                //插入订单明细表t_order_detail
                for (String cartId : cartIdList) {
                    OrderDetail detail = new OrderDetail();
                    detail.setOrderId(Long.parseLong(orderNo.toString()));
                    detail.setCreateTime(new Date());
                    detail.setUpdateTime(new Date());
                    detail.setCreateUser(loginUser.getId());
                    detail.setUpdateUser(loginUser.getId());
                    Cart cart = cartMapper.selectByPrimaryKey(Long.parseLong(cartId));
                    detail.setProductId(cart.getProductId());
                    detail.setProductName(cart.getProductName());
                    detail.setProductPrice(cart.getPrice());
                    detail.setCount(cart.getCount());
                    detailResult = orderDetailMapper.insertSelective(detail);
                }
            }else {
                return 0;
            }
            //2.删除提交订单成功的购物车信息
            List<Integer> ids = new ArrayList<>();
            if (detailResult > 0){
                for (String cartId : cartIdList) {
                    ids.add(Integer.parseInt(cartId));
                }
                cartMapper.batchDelById(ids);
            }else {
                return 0;
            }
        }catch (Exception e){
            log.info("提交订单异常，{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return 1;
    }

    @Override
    public PageInfo<Order> selectPage(Page page, Long id, Integer status) {
//        PageHelper.startPage(page.getCurrentPage(),page.getPageSize());
        //status  0:全部  1:待支付  2：待发货  3：待收货  4：待评价 6：已取消
        List<Order> orderList = orderMapper.selectPage(id,status);
        if (null != orderList && 0 != orderList.size()){
            for (Order order : orderList) {
                order.setTotalMoney(order.getTotalMoney()/100);
            }
        }
        return new PageInfo<>(orderList);
    }

    @Override
    public PageInfo getConditionPage(String orderNo, Byte orderStatus, Page page) {
        PageHelper.startPage(page.getCurrentPage(),page.getPageSize());
        //orderStatus 订单状态：1：未支付 2：已支付 3：已发货 4：已收货 5：已评价 6：已取消
        Map<String,Object> param = new HashMap<>();
        param.put("orderNo",orderNo);
        param.put("orderStatus",orderStatus);
        List<Order> orderList = orderMapper.getConditionPage(param);
        if (null != orderList && 0 != orderList.size()){
            for (Order order : orderList) {
                order.setTotalMoney(order.getTotalMoney()/100);
                //状态
                switch (order.getOrderStatus().intValue()){
                    case 1:
                        order.setStatusRemark("未支付");
                        break;
                    case 2:
                        order.setStatusRemark("已支付");
                        break;
                    case 3:
                        order.setStatusRemark("已发货");
                        break;
                    case 4:
                        order.setStatusRemark("已收货");
                        break;
                    case 5:
                        order.setStatusRemark("已评价");
                        break;
                    case 6:
                        order.setStatusRemark("已取消");
                        break;
                    default:
                        order.setStatusRemark("未知状态");
                }

                //物流
                LogisticsProviders providers = logisticsProvidersMapper.selectByPrimaryKey((long)order.getLogisticsProvidersId());
                order.setLogisticsProviders(providers.getName());
                //支付方式
                PayType payType = payTypeMapper.selectByPrimaryKey((long)order.getPayTypeId());
                order.setPayType(payType.getPayDesc());
            }
        }
        return new PageInfo<>(orderList);
    }
}
