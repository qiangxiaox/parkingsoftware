package com.jk.miaosha.vo;

import com.jk.miaosha.domain.MiaoshaUser;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName : GoodsDetailVo
 * @Author : xiaoqiang
 * @Date : 2018/11/5 21:54:54
 * @Description :
 * @Version ： 1.0
 */
@Data
public class GoodsDetailVo implements Serializable {
    private static final long serialVersionUID = 3959469170335222800L;

    private int miaoshaStatus; //秒杀状态 0表示还没开始 1 进行中 2结束
    private int remainSeconds; //秒杀剩余时间 0进行中 -1已结束
    private MiaoshaUser user;
    private GoodsVo goodsVo;
}
