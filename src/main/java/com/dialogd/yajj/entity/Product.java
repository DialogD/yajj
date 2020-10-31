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
public class Product implements Serializable {
    private static final long serialVersionUID = -6781591904160733016L;
    private Long id;

    private String name;

    private Long price;

    private Long salePrice;

    private String image;

    private String salePoint;

    private Integer typeId;

    private String typeName;

    private Boolean flag;

    private Date createTime;

    private Date updateTime;

    private Long userId;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}