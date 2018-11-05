package com.jk.miaosha.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName : MiaoshaOrder
 * @Author : xiaoqiang
 * @Date : 2018/11/5 11:22:22
 * @Description :
 * @Version ： 1.0
 */
@Data
public class MiaoshaOrder implements Serializable {
    private static final long serialVersionUID = -7971634386417456522L;
    private Long id;
    private Long userId;
    private Long orderId;
    private Long goodsId;
}
