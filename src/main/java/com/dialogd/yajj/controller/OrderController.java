package com.dialogd.yajj.controller;

import com.dialogd.yajj.common.Page;
import com.dialogd.yajj.entity.Cart;
import com.dialogd.yajj.entity.Order;
import com.dialogd.yajj.entity.OrderDetail;
import com.dialogd.yajj.entity.User;
import com.dialogd.yajj.mapper.CartMapper;
import com.dialogd.yajj.service.IOrderDetailSevice;
import com.dialogd.yajj.service.IOrderService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Date: 2020/4/18
 */
@Controller
@Api(tags = "订单管理")
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailSevice orderDetailSevice;

    @Autowired
    private CartMapper cartMapper;

    @ApiOperation(value = "提交订单", notes = "提交订单")
    @PostMapping("submitOrder")
    public String submitOrder(Order order, HttpServletRequest request){
        log.info("OrderController submitOrder param={}",order);
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (null == loginUser){
            return "front/login";
        }

        int result = orderService.submitOrder(order,loginUser);
        if (result > 0){
            List<Cart> cartList = cartMapper.queryCartByUserid(loginUser.getId());
            request.getSession().setAttribute("cartNum",cartList.size());
        }
        log.info("OrderController submitOrder result={}",result);
        return "redirect:/product/show";
    }

    @ApiOperation(value = "订单列表",notes = "订单列表")
    @RequestMapping(value = "/list")
    public String orderList(Integer status,Page page, Model model,HttpServletRequest request){
        log.info("OrderController orderList param status={}",status);
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (null == loginUser){
            return "forward:/front/login";
        }
        if (null == status){
            status = 0;
        }
        PageInfo<Order> pageInfo = orderService.selectPage(page,loginUser.getId(),status);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("status",status);
        model.addAttribute("url","order/list");
        return "front/center/order";
    }

    @ApiOperation(value = "订单后台分页", notes = "订单后台分页")
    @RequestMapping(value = "pageList")
    public String pageList(String orderNo,Byte orderStatus,Page page,Model model){
        log.info("OrderController pageList param orderNo={},orderStatus={}",orderNo,orderStatus);
        PageInfo<Order> pageInfo = orderService.getConditionPage(orderNo,orderStatus,page);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("orderNo",orderNo);
        model.addAttribute("orderStatus",orderStatus);
        model.addAttribute("url","order/pageList");
        return "back/order/orderList";
    }

    @ApiOperation(value = "订单详情信息列表", notes = "订单详情信息列表")
    @RequestMapping("detail/{id}")
    public String detail(@PathVariable("id") long id, Model model){
        log.info("OrderController detail param={}",id);
        List<OrderDetail> orderDetailList = orderDetailSevice.selectByOrderNo(id);
        model.addAttribute("orderDetailList",orderDetailList);
        model.addAttribute("size",orderDetailList.size());
        return "back/order/orderDetailList";
    }
}
