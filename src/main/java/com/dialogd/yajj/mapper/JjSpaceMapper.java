package com.dialogd.yajj.mapper;

import com.dialogd.yajj.dao.IBaseDao;
import com.dialogd.yajj.entity.JjSpace;

import java.util.List;
import java.util.Map;

public interface JjSpaceMapper extends IBaseDao<JjSpace> {

    List<JjSpace> spaceList();

    List<JjSpace> spacePageList(Map<String,Object> param);
}