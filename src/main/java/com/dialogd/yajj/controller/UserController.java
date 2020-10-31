package com.dialogd.yajj.controller;

import com.dialogd.yajj.common.Page;
import com.dialogd.yajj.common.ResultBean;
import com.dialogd.yajj.common.request.ReqLogin;
import com.dialogd.yajj.common.request.ReqRegister;
import com.dialogd.yajj.entity.Cart;
import com.dialogd.yajj.entity.User;
import com.dialogd.yajj.mapper.CartMapper;
import com.dialogd.yajj.service.IUserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @Date: 2020/3/1
 */
@Controller
@Api(tags = "用户中心")
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private CartMapper cartMapper;

    @ApiOperation(value = "用户后台分页", notes = "用户后台分页")
    @RequestMapping(value = "/pageList")
    public String userPageList(String username, String phone, Page page, Model model){
        PageInfo<User> pageInfo = userService.getConditionPage(username,phone,page);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("username",username);
        model.addAttribute("phone",phone);
        model.addAttribute("url","user/pageList");

        return "back/user/userList";
    }

    //To添加用户
    @ApiOperation(value = "To添加用户", notes = "To添加用户")
    @GetMapping("/toAdd")
    public String toAdd(){
        return "back/user/add";
    }

    @ApiOperation(value = "添加用户", notes = "添加用户")
    @PostMapping("/add")
    public String addUser(User user){
        log.info("back addUser param={}",user);
        userService.insertUser(user);

        return "forward:/user/pageList";
    }

    @ApiOperation(value = "判断用户名", notes = "判断用户名")
    @RequestMapping(value = "/isExistName",method = RequestMethod.GET)
    @ResponseBody
    public ResultBean isExistName(@RequestParam("username") String username){
        log.info("UserController isExistName param={}",username);
        return userService.isExistName(username);
    }

    @ApiOperation(value = "修改用户信息回显", notes = "修改用户信息回显")
    @RequestMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable("id") int id,Model model){
        log.info("toUpdateUser param={}",id);
        User user = userService.selectByPrimaryKey((long) id);
        model.addAttribute("user",user);
        return "back/user/update";
    }

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
    @PostMapping("/update")
    public String updateUser(User user){
        log.info("back updateUser param={}",user);
        user.setUpdateTime(new Date());
        userService.updateByPrimaryKeySelective(user);
        return "redirect:/user/pageList";
    }

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
    @PostMapping("/updatePersonal")
    @ResponseBody
    public ResultBean updatePersonal(User user){
        log.info("back updateUser param={}",user);
        user.setUpdateTime(new Date());
        int result = userService.updateByPrimaryKeySelective(user);
        if (result > 0){
            return ResultBean.SUCCESS("修改成功");
        }
        return ResultBean.FAIL("修改失败");
    }

    @ApiOperation(value = "删除用户信息", notes = "删除用户信息")
    @GetMapping("/deleteById")
    @ResponseBody
    public ResultBean deleteById(@RequestParam("id") int id){
        log.info("deleteByIdUser param={}",id);
        return userService.deleteById(id);
    }

    @ApiOperation(value = "批量删除用户", notes = "批量删除用户")
    @GetMapping("batchDelUser")
    @ResponseBody
    public ResultBean batchDelUser(@RequestParam("ids") List<Integer> ids){
        log.info("batchDelUser param={}",ids);
        return userService.batchDelUser(ids);
    }

    @ApiOperation(value = "登录", notes = "登录")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean login(@RequestBody ReqLogin login, HttpServletRequest request){
        if (StringUtils.isEmpty(login.getUsername()) || StringUtils.isEmpty(login.getPassword())){
            return ResultBean.FAIL("参数不能为空");
        }
        log.info("UserController login param={}",login);
        User loginUser = userService.login(login);
        if (null != loginUser){
            //登录成功
            request.getSession().setAttribute("loginUser",loginUser);
            log.info("login success to save session loginName={}",loginUser.getUsername());
            List<Cart> cartList = cartMapper.queryCartByUserid(loginUser.getId());
            request.getSession().setAttribute("cartNum",cartList.size());
            return ResultBean.SUCCESS("success");
        }else {
            return ResultBean.FAIL("账号或密码错误");
        }

    }

    @ApiOperation(value = "注册", notes = "注册")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean register(@RequestBody ReqRegister register){
        log.info("UserController reqRegister param={}",register);
        return userService.register(register);
    }

    @ApiOperation(value = "注销", notes = "注销")
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request){
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (null != loginUser){
            request.getSession().removeAttribute("loginUser");
            request.getSession().removeAttribute("cartNum");
        }
        return "front/index";
    }
}
