package com.dialogd.yajj.service;

import com.dialogd.yajj.common.Page;
import com.dialogd.yajj.common.ResultBean;
import com.dialogd.yajj.common.request.ReqLogin;
import com.dialogd.yajj.common.request.ReqRegister;
import com.dialogd.yajj.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Date: 2020/3/1
 */
public interface IUserService extends IBaseService<User> {

    PageInfo getConditionPage(String username, String phone, Page page);

    ResultBean isExistName(String username);

    ResultBean deleteById(int id);

    ResultBean batchDelUser(List<Integer> ids);

    User login(ReqLogin login);

    ResultBean register(ReqRegister register);

    void insertUser(User user);
}
