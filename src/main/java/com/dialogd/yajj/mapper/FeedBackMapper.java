package com.dialogd.yajj.mapper;

import com.dialogd.yajj.dao.IBaseDao;
import com.dialogd.yajj.entity.FeedBack;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FeedBackMapper extends IBaseDao<FeedBack> {

    List<FeedBack> getConditionPage(@Param("name") String name,@Param("phone") String phone);

    List<FeedBack> selectByUserId(int id);
}