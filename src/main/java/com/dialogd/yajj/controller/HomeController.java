package com.dialogd.yajj.controller;

import com.dialogd.yajj.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @Date: 2020/4/5
 */
@Controller
@Api(tags = "基础home")
@RequestMapping("/home")
@Slf4j
public class HomeController {

    @ApiOperation(value = "进入后台首页", notes = "进入后台首页")
    @RequestMapping(value = "/backIndex",method = RequestMethod.GET)
    public String backIndex(HttpServletRequest request, Model model){
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (null == loginUser){
            return "front/login";
        }
        model.addAttribute("logSession",loginUser);
        model.addAttribute("role",loginUser.getRole());
        return "back/backIndex";
    }

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String frontIndex(){
        return "front/index";
    }

    @RequestMapping(value = "/feed",method = RequestMethod.GET)
    public String our(HttpServletRequest request){
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (null != loginUser){
            return "front/feedBack";
        }else {
            return "front/login";
        }
    }

    @RequestMapping(value = "/space",method = RequestMethod.GET)
    public String news(){
        return "redirect:/space/show";
    }

    @RequestMapping(value = "/center",method = RequestMethod.GET)
    public String userCenter(HttpServletRequest request){
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (null != loginUser){
            return "front/userCenter";
        }else {
            return "front/login";
        }
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "front/login";
    }
}
