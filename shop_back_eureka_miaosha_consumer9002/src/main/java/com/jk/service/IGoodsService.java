package com.jk.service;

import com.jk.miaosha.RestGoodsService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "config-miaosha-provider")
public interface IGoodsService extends RestGoodsService {
}
