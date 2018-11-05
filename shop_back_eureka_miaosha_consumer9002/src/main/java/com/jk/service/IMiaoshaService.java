package com.jk.service;

import com.jk.miaosha.RestMiaoshaService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "config-miaosha-provider")
public interface IMiaoshaService extends RestMiaoshaService {
}
