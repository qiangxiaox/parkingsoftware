package com.jk.miaosha;

import com.jk.miaosha.domain.MiaoshaOrder;
import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.miaosha.domain.OrderInfo;
import com.jk.miaosha.result.CodeMsg;
import com.jk.miaosha.result.Result;
import com.jk.miaosha.vo.GoodsVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface RestMiaoshaService {

    /**
     * 秒杀
     * @param userId
     * @param goodsVo
     * @return
     */
    @PostMapping("/miaosha/api/domiaosha")
    public Result<OrderInfo> doMiaosha(@RequestParam("userId") long userId, @RequestBody GoodsVo goodsVo);

    /**
     * 获取秒杀结果
     * @param userId
     * @param goodsId
     * @return
     */
    @GetMapping("/miaosha/api/getMiaoshaResult")
    public Result<Long> getMiaoshaResult(@RequestParam("userId") long userId, @RequestParam("goodsId") long goodsId);

    /**
     * 重置数据库秒杀内容
     * @param goodsVoList
     * @return
     */
    @PostMapping("/miaosha/api/reset")
    public Result<Boolean> doMiaoshaReset(@RequestBody List<GoodsVo> goodsVoList);

    /**
     * 创建限流的路径
     * @param user
     * @param goodsId
     * @return
     */
    @PostMapping("/miaosha/api/createPath")
    public Result<String> createMiaoshaPath(@RequestBody MiaoshaUser user, @RequestParam("goodsId") long goodsId);

    /**
     * 检查限流的路径是否重复
     * @param user
     * @param goodsId
     * @return
     */
    @PostMapping("/miaosha/api/checkPath")
    public Result<Boolean> checkPath(@RequestBody MiaoshaUser user, @RequestParam("goodsId") long goodsId,@RequestParam("path") String path);

    @GetMapping("/order/api/getMiaoshaOrderByUserIdAndGoodsId")
    public Result<MiaoshaOrder> getMiaoshaOrderByUserIdAndGoodsId(@RequestParam(value = "userId")long userId, @RequestParam(value = "goodsId") long goodsId);

    @GetMapping("/order/api/removeAllMiaoshaOrders")
    public Result<CodeMsg> removeAllMiaoshaOrders();

}
