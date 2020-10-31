package com.dialogd.yajj.mapper;

import com.dialogd.yajj.dao.IBaseDao;
import com.dialogd.yajj.entity.Address;

import java.util.List;

public interface AddressMapper extends IBaseDao<Address> {

    List<Address> selectByUserId(Long userId);
}