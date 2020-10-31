package com.dialogd.yajj.service;

import com.dialogd.yajj.common.Page;
import com.dialogd.yajj.common.ResultBean;
import com.dialogd.yajj.entity.Product;
import com.dialogd.yajj.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Date: 2020/4/6
 */
public interface IProductService extends IBaseService<Product> {

    PageInfo<Product> getPageProduct(int type, Page page);

    ResultBean addCart(int productId, int count,long userId);

    ResultBean delCart(int id);

    PageInfo getConditionPage(String name, Integer state, Page page, User loginUser);

    ResultBean deleteById(int id);

    ResultBean batchDelProduct(List<Integer> ids);

    ResultBean isExistName(String name);
}
