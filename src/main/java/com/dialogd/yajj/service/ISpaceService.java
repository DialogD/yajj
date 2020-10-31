package com.dialogd.yajj.service;

import com.dialogd.yajj.common.Page;
import com.dialogd.yajj.common.ResultBean;
import com.dialogd.yajj.entity.JjSpace;
import com.github.pagehelper.PageInfo;

/**
 * @Date 2020/5/17
 */
public interface ISpaceService extends IBaseService<JjSpace> {

    PageInfo<JjSpace> getPageSpace(Page page);

    PageInfo<JjSpace> spacePageList(String username, Page page);

    ResultBean deleteSpaceById(int id);
}
