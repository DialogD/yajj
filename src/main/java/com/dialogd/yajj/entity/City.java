package com.dialogd.yajj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class City implements Serializable {
    private Integer id;

    private String name;

    private Integer provinceId;

    private Boolean flag;

}