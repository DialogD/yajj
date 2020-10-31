package com.dialogd.yajj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JjSpace implements Serializable {
    private static final long serialVersionUID = -1136079163882902387L;
    private Long id;

    private Long userId;

    private String username;

    private String image;

    private String jjDesc;

    private Boolean flag;

    private Date createTime;

    private Date updateTime;

}