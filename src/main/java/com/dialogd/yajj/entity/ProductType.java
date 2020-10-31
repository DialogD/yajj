package com.dialogd.yajj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductType implements Serializable {
    private static final long serialVersionUID = -897882008774939005L;
    private Integer id;

    private Integer pid;

    private String name;

    private Boolean flag;
}