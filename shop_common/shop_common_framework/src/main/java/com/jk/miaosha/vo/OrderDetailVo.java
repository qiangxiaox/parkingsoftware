package com.jk.miaosha.vo;

import com.jk.miaosha.domain.OrderInfo;

import java.io.Serializable;

public class OrderDetailVo implements Serializable {

	private static final long serialVersionUID = 2592735612393635646L;

	private GoodsVo goods;
	private OrderInfo order;
	public GoodsVo getGoods() {
		return goods;
	}
	public void setGoods(GoodsVo goods) {
		this.goods = goods;
	}
	public OrderInfo getOrder() {
		return order;
	}
	public void setOrder(OrderInfo order) {
		this.order = order;
	}
}
