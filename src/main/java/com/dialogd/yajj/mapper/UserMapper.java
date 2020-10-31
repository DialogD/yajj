package com.dialogd.yajj.mapper;

import com.dialogd.yajj.dao.IBaseDao;
import com.dialogd.yajj.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface UserMapper extends IBaseDao<User> {

    /**
     * 根据用户名和手机号查询信息
     * @param param
     * @return
     */
    List<User> getConditionPage(Map<String, Object> param);

    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    int isExistName(String username);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    void batchDelUser(@Param("ids") List<Integer> ids);

    User selectByLogin(@Param("username") String username, @Param("password") String password);

    int isExistPhone(String registerUsername);
}