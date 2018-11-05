package com.jk.miaosha.dao;

import com.jk.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;

@Mapper
public interface IGoodsDAO {

    /**
     * 查询所有的秒杀商品
     * @return
     */
    @Select("SELECT g.*,mg.stock_count,mg.miaosha_price,mg.start_date,mg.end_date " +
            " FROM goods g,miaosha_goods mg " +
            " WHERE g.id=mg.goods_id ")
    public List<GoodsVo> findAllGoodsVo();

    /**
     * 根据商品Id查询该秒杀商品的信息
     * @param goodsId
     * @return
     */
    @Select("SELECT g.*,mg.stock_count,mg.miaosha_price,mg.start_date,mg.end_date " +
            " FROM goods g,miaosha_goods mg " +
            " WHERE g.id=mg.goods_id AND g.id = #{goodsId}")
    public GoodsVo findGoodsVoById(@Param(value = "goodsId") long goodsId);

    /**
     * 更新秒杀商品的库存
     * @param goodsId
     * @return
     */
    @Update("UPDATE miaosha_goods SET stock_count = stock_count - 1 WHERE goods_id = #{goodsId} AND stock_count > 0")
    public int doUpdateMiaoshaGoodsStock(@Param(value = "goodsId") long goodsId);

}
