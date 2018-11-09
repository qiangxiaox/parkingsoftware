package com.jk.api;

import com.jk.goods.RestGoodsService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "config-goods-provider")
public interface InvokeGoodsAPI extends RestGoodsService {
}
