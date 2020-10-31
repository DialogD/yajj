package com.dialogd.yajj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    private static final long serialVersionUID = -5102586839912326731L;
    private Long id;

    private Long userId;

    private Integer payTypeId;

    private Integer logisticsProvidersId;

    private String orderNo;

    private String logisticsNo;

    private Long logisticsCost;

    private Long totalMoney;

    /**
     * 订单状态：1：未支付 2：已支付 3：已发货 4：已收货 5：已评价 6：已取消
     */
    private Byte orderStatus;

    private String reviceAddress;

    private String revicer;

    private String phone;

    private Boolean flag;

    private Date createTime;

    private Date updateTime;

    @Transient
    private String payType;

    @Transient
    private String logisticsProviders;

    @Transient
    private String statusRemark;
}