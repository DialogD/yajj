package com.dialogd.yajj.controller;

import com.dialogd.yajj.common.Page;
import com.dialogd.yajj.common.ResultBean;
import com.dialogd.yajj.entity.JjSpace;
import com.dialogd.yajj.entity.User;
import com.dialogd.yajj.service.ISpaceService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Date 2020/5/17
 */
@Controller
@RequestMapping("/space")
@Api(tags = "家居空间管理")
@Slf4j
public class SpaceController {

    @Autowired
    private ISpaceService spaceService;

    @ApiOperation(value = "家居空间展示", notes = "家居空间展示")
    @RequestMapping(value = "/show")
    public String showSpace(Page page, Model model,HttpServletRequest request){
        log.info("SpaceController showSpace...");
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (null == loginUser){
            return "front/login";
        }
        PageInfo<JjSpace> pageInfo = spaceService.getPageSpace(page);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("url","space/show");
        return "front/jjSpace";
    }

    @ApiOperation(value = "家居空间后台分页", notes = "家居空间后台分页")
    @RequestMapping(value = "/pageList")
    public String spacePageList(String username, Page page, Model model){
        PageInfo<JjSpace> pageInfo = spaceService.spacePageList(username,page);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("username",username);
        model.addAttribute("url","space/pageList");

        return "back/space/spaceList";
    }

    @ApiOperation(value = "删除家居空间信息", notes = "删除家居空间信息")
    @GetMapping("/deleteById")
    @ResponseBody
    public ResultBean deleteSpaceById(@RequestParam("id") int id){
        log.info("deleteByIdJjSpace param id={}",id);
        return spaceService.deleteSpaceById(id);
    }

    @ApiOperation(value = "发布家居空间信息", notes = "发布家居空间信息")
    @GetMapping("/publish")
    public String publish(){
        return "front/publish";
    }

    @ApiOperation(value = "家居空间信息提交", notes = "家居空间信息提交")
    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    private String submitSpace(JjSpace space, HttpServletRequest request){
        log.info("SpaceController submitSpace space={}",space);
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (null == loginUser){
            return "front/login";
        }
        space.setUserId(loginUser.getId());
        space.setUsername(loginUser.getUsername());
        space.setCreateTime(new Date());
        space.setUpdateTime(new Date());
        spaceService.insertSelective(space);
        return "forward:/space/show";
    }
}
