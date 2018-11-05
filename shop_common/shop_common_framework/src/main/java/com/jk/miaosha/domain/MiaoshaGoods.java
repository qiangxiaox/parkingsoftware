package com.jk.miaosha.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName : MiaoshaGoods
 * @Author : xiaoqiang
 * @Date : 2018/11/5 11:21:21
 * @Description :
 * @Version ： 1.0
 */
@Data
public class MiaoshaGoods implements Serializable {
    private static final long serialVersionUID = -2355663840853742901L;
    private Long id;
    private Long goodsId;
    private Integer stockCount;
    private Double miaoshaPrice;
    private Date startDate;
    private Date endDate;
}
