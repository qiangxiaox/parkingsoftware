package com.jk.rabbitmq.miaosha;

import com.jk.miaosha.domain.MiaoshaOrder;
import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.miaosha.result.Result;
import com.jk.miaosha.vo.GoodsVo;
import com.jk.service.IGoodsService;
import com.jk.service.IMiaoshaService;
import com.jk.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {

		private static Logger log = LoggerFactory.getLogger(MQReceiver.class);
		

		@Autowired
		private IMiaoshaService miaoshaService;
		@Autowired
		private IGoodsService goodsService;
		@Autowired
		private IOrderService orderService;

		
		@RabbitListener(queues=MQConfig.MIAOSHA_QUEUE)
		public void receive(String message) {
			log.info("receive message:"+message);
			MiaoshaMessage mm  = MQTools.stringToBean(message, MiaoshaMessage.class);
			MiaoshaUser user = mm.getUser();
			long goodsId = mm.getGoodsId();
		//查询库存
			GoodsVo goods =  goodsService.getGoodsVOById(goodsId).getData();
			int stock = goods.getStockCount();
	    	if(stock <= 0) {
	    		return;
	    	}
		//判断是否已经秒杀到了,这里是查的redis所以，再次查询也无所谓
			Result<MiaoshaOrder> orderResult = miaoshaService.getMiaoshaOrderByUserIdAndGoodsId(user.getId(), goodsId);
			MiaoshaOrder order = orderResult.getData();
			if(order != null) {
	    		return;
	    	}
	    	//减库存 下订单 写入秒杀订单
	    	miaoshaService.doMiaosha(user.getId(), goods);
		}
	
//		@RabbitListener(queues=MQConfig.QUEUE)
//		public void receive(String message) {
//			log.info("receive message:"+message);
//		}
//		
//		@RabbitListener(queues=MQConfig.TOPIC_QUEUE1)
//		public void receiveTopic1(String message) {
//			log.info(" topic  queue1 message:"+message);
//		}
//		
//		@RabbitListener(queues=MQConfig.TOPIC_QUEUE2)
//		public void receiveTopic2(String message) {
//			log.info(" topic  queue2 message:"+message);
//		}
//		
//		@RabbitListener(queues=MQConfig.HEADER_QUEUE)
//		public void receiveHeaderQueue(byte[] message) {
//			log.info(" header  queue message:"+new String(message));
//		}
//		
		
}
