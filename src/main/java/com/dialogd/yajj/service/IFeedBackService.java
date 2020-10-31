package com.dialogd.yajj.service;

import com.dialogd.yajj.common.Page;
import com.dialogd.yajj.common.ResultBean;
import com.dialogd.yajj.entity.FeedBack;
import com.dialogd.yajj.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IFeedBackService extends IBaseService<FeedBack> {

    ResultBean submitFeedBack(User loginUser, FeedBack feedBack);

    PageInfo<FeedBack> getConditionPage(String name, String phone, Page page);

    void replyInfo(Long id, String replyMark);

    List<FeedBack> selectByUserId(Long id);
}
