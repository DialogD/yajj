package com.dialogd.yajj.entity;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 3969411774229388818L;
    //主键id
    private Long id;
    //用户名
    private String username;
    //手机号
    private String password;
    //性别
    private String gender;
    //手机号
    private String phone;
    //邮箱
    private String email;
    //头像图片地址
    private String image;
    //标识
    private Boolean flag;
    //角色
    private Byte role;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}