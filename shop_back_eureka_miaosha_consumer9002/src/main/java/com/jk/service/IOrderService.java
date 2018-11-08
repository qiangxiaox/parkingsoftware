package com.jk.service;

import com.jk.order.RestOrderService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "config-miaosha-provider")
public interface IOrderService extends RestOrderService {
}
