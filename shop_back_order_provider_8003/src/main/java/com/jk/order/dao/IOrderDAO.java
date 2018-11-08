package com.jk.order.dao;

import com.jk.miaosha.domain.MiaoshaOrder;
import com.jk.miaosha.domain.OrderInfo;
import org.apache.ibatis.annotations.*;

public interface IOrderDAO {

    /**
     * 新增订单信息
     * @param orderInfo
     * @return
     */
    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    public long doCreateOrder(OrderInfo orderInfo);

    /**
     * 根据订单查询订单信息
     * @param orderId
     * @return
     */
    @Select("select * from order_info where id = #{orderId}")
    public OrderInfo findById(@Param("orderId") long orderId);

    /**
     * 秒杀内容重置，删除订单信息
     */
    @Delete("DELETE FROM order_info WHERE 1=1")
    public void doRemoveAllOrders();

}
