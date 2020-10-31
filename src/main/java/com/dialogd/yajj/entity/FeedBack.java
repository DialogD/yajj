package com.dialogd.yajj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedBack implements Serializable {
    private Long id;

    private Long userId;

    private String name;

    private String phone;

    private String titile;

    private String content;

    private Boolean flag;

    private Date createTime;

    private String replyMark;
}