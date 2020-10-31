package com.dialogd.yajj.controller;

import com.dialogd.yajj.entity.User;
import com.dialogd.yajj.mapper.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @Date: 2020/4/11
 */
@Controller
@Api(tags = "会员中心")
@RequestMapping("/center")
@Slf4j
public class CenterController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/personal",method = RequestMethod.GET)
    @ApiOperation(value = "个人资料显示", notes = "个人资料显示")
    public String personal(HttpServletRequest request, Model model){
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        User user = userMapper.selectByPrimaryKey(loginUser.getId());
        model.addAttribute("user",user);
        return "front/center/personal";
    }
}
