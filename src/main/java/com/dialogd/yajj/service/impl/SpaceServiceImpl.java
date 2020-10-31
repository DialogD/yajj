package com.dialogd.yajj.service.impl;

import com.dialogd.yajj.common.Page;
import com.dialogd.yajj.common.ResultBean;
import com.dialogd.yajj.dao.IBaseDao;
import com.dialogd.yajj.entity.JjSpace;
import com.dialogd.yajj.mapper.JjSpaceMapper;
import com.dialogd.yajj.service.ISpaceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2020/5/17
 */
@Service
@Slf4j
public class SpaceServiceImpl extends BaseServiceImpl<JjSpace> implements ISpaceService {

    @Autowired
    private JjSpaceMapper spaceMapper;

    @Override
    public IBaseDao<JjSpace> getDao() {
        return spaceMapper;
    }

    @Override
    public PageInfo<JjSpace> getPageSpace(Page page) {
        PageHelper.startPage(page.getCurrentPage(),page.getPageSize());
        List<JjSpace> spaceList = spaceMapper.spaceList();
        return new PageInfo<>(spaceList);
    }

    @Override
    public PageInfo<JjSpace> spacePageList(String username, Page page) {
        PageHelper.startPage(page.getCurrentPage(),page.getPageSize());
        Map<String,Object> param = new HashMap<>();
        param.put("username",username);
        List<JjSpace> spaceList = spaceMapper.spacePageList(param);
        return new PageInfo<>(spaceList);
    }

    @Override
    public ResultBean deleteSpaceById(int id) {
        JjSpace space = new JjSpace();
        space.setId((long) id);
        space.setFlag(false);
        space.setUpdateTime(new Date());
        int result = spaceMapper.updateByPrimaryKeySelective(space);
        if (result > 0){
            return ResultBean.SUCCESS("删除成功");
        }
        return ResultBean.SUCCESS("删除失败");
    }
}
