package com.jk.miaosha.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName : OrderInfo
 * @Author : xiaoqiang
 * @Date : 2018/11/5 11:21:21
 * @Description :
 * @Version ： 1.0
 */
@Data
public class OrderInfo implements Serializable {
    private static final long serialVersionUID = -3702365003469113520L;
    private Long id;
    private Long userId;
    private Long goodsId;
    private Long deliveryAddrId;
    private String goodsName;
    private Integer goodsCount;
    private Double goodsPrice;
    private Integer orderChannel;
    private Integer status;
    private Date createDate;
    private Date payDate;
}
