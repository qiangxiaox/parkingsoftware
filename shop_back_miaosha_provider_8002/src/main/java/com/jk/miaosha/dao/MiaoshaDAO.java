package com.jk.miaosha.dao;

import com.jk.miaosha.domain.MiaoshaOrder;
import org.apache.ibatis.annotations.*;

/**
 * @ClassName : MiaoshaDAO
 * @Author : xiaoqiang
 * @Date : 2018/11/7 20:10:10
 * @Description :
 * @Version ： 1.0
 */
@Mapper
public interface MiaoshaDAO {

    /**
     * 新增秒杀订单信息
     * @param miaoshaOrder
     * @return
     */
    @Insert("insert into miaosha_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    public long doCreateMiaoshaOrder(MiaoshaOrder miaoshaOrder);

    /**
     * 根据用户Id和商品Id查询该用户是否存在有MiaoshaOrder订单数据
     * @param userId
     * @param goodsId
     * @return
     */
    @Select("SELECT * FROM miaosha_order mo WHERE mo.user_id = #{userId} AND mo.goods_id=#{goodsId}")
    public MiaoshaOrder findMiaoshaOrderByUserIdAndGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

    /**
     * 秒杀内容重置，删除秒杀订单信息
     */
    @Delete("DELETE FROM miaosha_order WHERE 1=1")
    public void doRemoveAllMiaoshaOrders();

}
