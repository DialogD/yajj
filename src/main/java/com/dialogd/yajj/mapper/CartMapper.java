package com.dialogd.yajj.mapper;

import com.dialogd.yajj.dao.IBaseDao;
import com.dialogd.yajj.entity.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper extends IBaseDao<Cart> {

    Cart selectIsExistTwoId(@Param("userId") long userId,@Param("productId") long productId);

    List<Cart> queryCartByUserid(Long userId);

    List<Cart> selectByIds(@Param("ids") List<Integer> ids);

    void batchDelById(@Param("ids") List<Integer> ids);
}