package com.jk.miaosha.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName : Goods
 * @Author : xiaoqiang
 * @Date : 2018/11/5 11:21:21
 * @Description :
 * @Version ： 1.0
 */
@Data
public class Goods implements Serializable {

    private static final long serialVersionUID = 7928814428928337086L;
    private Long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private Double goodsPrice;
    private Integer goodsStock;
}
