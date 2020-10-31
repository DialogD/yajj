package com.dialogd.yajj.service.impl;


import com.dialogd.yajj.common.Page;
import com.dialogd.yajj.common.ResultBean;
import com.dialogd.yajj.dao.IBaseDao;
import com.dialogd.yajj.entity.FeedBack;
import com.dialogd.yajj.entity.User;
import com.dialogd.yajj.mapper.FeedBackMapper;
import com.dialogd.yajj.service.IFeedBackService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class FeedBackServiceImpl extends BaseServiceImpl<FeedBack> implements IFeedBackService {

    @Autowired
    private FeedBackMapper feedBackMapper;

    @Override
    public IBaseDao<FeedBack> getDao() {
        return feedBackMapper;
    }

    @Override
    public ResultBean submitFeedBack(User loginUser, FeedBack feedBack) {
        feedBack.setName(loginUser.getUsername());
        feedBack.setUserId(loginUser.getId());
        feedBack.setCreateTime(new Date());
        int result = feedBackMapper.insertSelective(feedBack);
        if (result > 0){
            return ResultBean.SUCCESS("提交成功");
        }
        return ResultBean.FAIL("提交失败");
    }

    @Override
    public PageInfo<FeedBack> getConditionPage(String name, String phone, Page page) {
        PageHelper.startPage(page.getCurrentPage(),page.getPageSize());
        List<FeedBack> feedBackList = feedBackMapper.getConditionPage(name,phone);
        return new PageInfo<>(feedBackList);
    }

    @Override
    public void replyInfo(Long id, String replyMark) {
        FeedBack feedBack = new FeedBack();
        feedBack.setCreateTime(new Date());
        feedBack.setId(id);
        feedBack.setReplyMark(replyMark);
        feedBack.setFlag(false);
        feedBackMapper.updateByPrimaryKeySelective(feedBack);
    }

    @Override
    public List<FeedBack> selectByUserId(Long id) {
        return feedBackMapper.selectByUserId(id.intValue());
    }
}
