package com.dialogd.yajj.common.emuns;

import java.io.Serializable;

public interface BaseEnum extends Serializable {
    int  getCode();

    String getMsg();

    String getSubCode();
}