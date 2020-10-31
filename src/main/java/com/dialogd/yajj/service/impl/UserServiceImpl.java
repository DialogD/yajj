package com.dialogd.yajj.service.impl;

import com.dialogd.yajj.common.Page;
import com.dialogd.yajj.common.ResultBean;
import com.dialogd.yajj.common.request.ReqLogin;
import com.dialogd.yajj.common.request.ReqRegister;
import com.dialogd.yajj.dao.IBaseDao;
import com.dialogd.yajj.entity.User;
import com.dialogd.yajj.mapper.UserMapper;
import com.dialogd.yajj.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date: 2020/3/1
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public IBaseDao<User> getDao() {
        return userMapper;
    }

    @Override
    public PageInfo getConditionPage(String username, String phone, Page page) {
        PageHelper.startPage(page.getCurrentPage(),page.getPageSize());
        Map<String,Object> param = new HashMap<>();
        param.put("username",username);
        param.put("phone",phone);
        List<User> userList = userMapper.getConditionPage(param);
        return new PageInfo(userList);
    }

    @Override
    public ResultBean isExistName(String username) {
        int result = userMapper.isExistName(username);
        if (result>0){
            return ResultBean.FAIL("用户名已存在");
        }
        return ResultBean.SUCCESS();
    }

    @Override
    public ResultBean deleteById(int id) {
        //flag=0，假删除
        User user = new User();
        user.setId((long) id);
        user.setFlag(false);
        int result = userMapper.updateByPrimaryKeySelective(user);
        if (result > 0){
            return ResultBean.SUCCESS("删除成功!");
        }
        return ResultBean.FAIL("删除失败!");
    }

    @Override
    public ResultBean batchDelUser(List<Integer> ids) {
        userMapper.batchDelUser(ids);

         return ResultBean.SUCCESS("批量删除成功!");
    }

    @Override
    public User login(ReqLogin login) {
        User loginUser = userMapper.selectByLogin(login.getUsername(),login.getPassword());
        return loginUser;
    }

    @Override
    public ResultBean register(ReqRegister register) {
        if (StringUtils.isAnyEmpty(register.getRegisterUsername(),register.getRegisterPassword(),
                register.getRegisterPhone(),register.getRegisterWellPassword())){
            return ResultBean.FAIL("参数不能为空");
        }

        if (!register.getRegisterPassword().equals(register.getRegisterWellPassword())){
            return ResultBean.FAIL("两次密码输入不一致");
        }
        //判断密码
        if (!isPwdPattern(register.getRegisterPassword())){
            return ResultBean.FAIL("密码为6-18位数字和大小写英文组合");
        }
        //判断用户名是否存在
        if (userMapper.isExistName(register.getRegisterPhone())>0){
            return ResultBean.FAIL("用户名已存在");
        }

        //判断手机号是否存在
        if (userMapper.isExistPhone(register.getRegisterUsername())>0){
            return ResultBean.FAIL("手机号已存在");
        }
        User registerUser = new User();
        registerUser.setUsername(register.getRegisterUsername());
        registerUser.setPassword(register.getRegisterPassword());
        registerUser.setPhone(register.getRegisterPhone());
        //插入数据
        int result = userMapper.insertSelective(registerUser);
        if (result > 0){
            return ResultBean.SUCCESS("success");
        }
        return ResultBean.FAIL("注册失败");
    }

    @Override
    public void insertUser(User user) {
        userMapper.insertSelective(user);
    }

    //判断密码是否为6-18位数字和大小写英文组合
    public static boolean isPwdPattern(String str) {
        //用来表示是否包含数字
        boolean isNumber = false;
        //用来表示是否包含字母大写
        boolean isLetterUp = false;
        //用来表示是否包含字母小写
        boolean isLetterLower = false;
        String regex = "^[a-zA-Z0-9]{6,18}$";
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isNumber = true;
            } else if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                if (str.charAt(i)>='a' && str.charAt(i) <= 'z'){
                    isLetterLower = true;
                }
                if (str.charAt(i)>='A' && str.charAt(i) <= 'Z'){
                    isLetterUp = true;
                }
            }
            if (isNumber && isLetterUp && isLetterLower){
                break;
            }
        }
        return isNumber && isLetterUp && isLetterLower && str.matches(regex);
    }
}
