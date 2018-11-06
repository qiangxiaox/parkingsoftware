package com.jk.miaosha.service.impl;

import com.jk.miaosha.dao.IGoodsDAO;
import com.jk.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName : MiaoshaGoodsServiceImpl
 * @Author : xiaoqiang
 * @Date : 2018/11/5 11:42:42
 * @Description :
 * @Version ： 1.0
 */
@Service
public class GoodsServiceImpl {
    @Autowired
    private IGoodsDAO miaoshaGoodsDAO;
    /**
     * 查询所有的秒杀商品
     * @return
     */
    public List<GoodsVo> getAllGoodsVO(){
        return this.miaoshaGoodsDAO.findAllGoodsVo();
    }
    /**
     * 根据商品Id查询该秒杀商品的信息
     * @param goodsId
     * @return
     */
    public GoodsVo getGoodsVOById(long goodsId){
        return this.miaoshaGoodsDAO.findGoodsVoById(goodsId);
    }
    /**
     * 更新秒杀商品的库存
     * @param goodsId
     * @return
     */
    public int reduceMiaoshaGoodsStock(long goodsId){
        return this.miaoshaGoodsDAO.doUpdateMiaoshaGoodsStock(goodsId);
    }

    public void resetStock(List<GoodsVo> goodsList) {
        for (GoodsVo goodsVo : goodsList){
            this.miaoshaGoodsDAO.doUpdateStock(goodsVo.getStockCount(), goodsVo.getId());
        }
    }
}
