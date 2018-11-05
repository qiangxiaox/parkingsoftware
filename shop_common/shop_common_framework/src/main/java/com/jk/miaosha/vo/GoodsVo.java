package com.jk.miaosha.vo;

import com.jk.miaosha.domain.Goods;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName : GoodsVo
 * @Author : xiaoqiang
 * @Date : 2018/11/5 11:31:31
 * @Description :
 * @Version ： 1.0
 */
@Data
public class GoodsVo extends Goods implements Serializable {
    private Integer stockCount; //秒杀的库存数量
    private Double miaoshaPrice;
    private Date startDate;
    private Date endDate;
}
