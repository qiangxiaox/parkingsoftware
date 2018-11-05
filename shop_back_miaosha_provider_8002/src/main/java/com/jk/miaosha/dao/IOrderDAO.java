package com.jk.miaosha.dao;

import com.jk.miaosha.domain.MiaoshaOrder;
import com.jk.miaosha.domain.OrderInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

public interface IOrderDAO {

    /**
     * 根据用户Id和商品Id查询该用户是否存在有MiaoshaOrder订单数据
     * @param userId
     * @param goodsId
     * @return
     */
    @Select("SELECT * FROM miaosha_order mo WHERE mo.user_id = #{userId} AND mo.goods_id=#{goodsId}")
    public MiaoshaOrder findMiaoshaOrderByUserIdAndGoodsId(@Param("userId") long userId, @Param("goodsId")long goodsId);

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
     * 新增秒杀订单信息
     * @param miaoshaOrder
     * @return
     */
    @Insert("insert into miaosha_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    public int doCreateMiaoshaOrder(MiaoshaOrder miaoshaOrder);

    /**
     * 根据订单查询订单信息
     * @param orderId
     * @return
     */
    @Select("select * from order_info where id = #{orderId}")
    public OrderInfo findById(@Param("orderId") long orderId);
}
