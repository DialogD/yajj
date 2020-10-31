package com.dialogd.yajj.controller;

import com.dialogd.yajj.common.Page;
import com.dialogd.yajj.common.ResultBean;
import com.dialogd.yajj.entity.FeedBack;
import com.dialogd.yajj.entity.User;
import com.dialogd.yajj.service.IFeedBackService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/feedback")
@Api(tags = "反馈管理")
@Slf4j
public class FeedBackController {

    @Autowired
    private IFeedBackService feedBackService;

    @ApiOperation(value = "反馈信息提交", notes = "反馈信息提交")
    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    @ResponseBody
    private ResultBean submitFeed(FeedBack feedBack, HttpServletRequest request){
        log.info("FeedBackController submit feedBack={}",feedBack);
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (null == loginUser){
            return ResultBean.FAIL("请先登录");
        }
        return feedBackService.submitFeedBack(loginUser,feedBack);
    }

    @ApiOperation(value = "反馈信息后台分页", notes = "反馈信息后台分页")
    @RequestMapping(value = "pageList")
    public String pageList(String name, String phone, Page page, Model model){
        log.info("FeedBackController pageList param name={},phone={}",name,phone);
        PageInfo<FeedBack> pageInfo = feedBackService.getConditionPage(name,phone,page);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("name",name);
        model.addAttribute("phone",phone);
        model.addAttribute("url","feedback/pageList");
        return "back/feedback/feedbackList";
    }

    @ApiOperation(value = "回复信息页面",notes = "回复信息页面")
    @RequestMapping(value = "/reply/{id}",method = RequestMethod.GET)
    public String reply(@PathVariable("id") Long id, Model model){
        FeedBack feedBack = feedBackService.selectByPrimaryKey(id);
        model.addAttribute("feed",feedBack);
        return "back/feedback/reply";
    }

    @ApiOperation(value = "回复信息",notes = "回复信息")
    @RequestMapping(value = "/reply",method = RequestMethod.POST)
    public String reply(Long id,String replyMark){
        feedBackService.replyInfo(id,replyMark);
        return "redirect:/feed/pageList";
    }

    @ApiOperation(value = "显示反馈信息", notes = "显示反馈信息")
    @RequestMapping(value = "showFeed",method = RequestMethod.GET)
    public String show(Model model,HttpServletRequest request){
        log.info("FeedBackController show ...");
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (null == loginUser){
            return "front/login";
        }
        List<FeedBack> feedBackList = feedBackService.selectByUserId(loginUser.getId());
        model.addAttribute("feedBackList",feedBackList);
        return "front/center/showFeed";
    }
}
