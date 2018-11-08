package com.jk.api;

import com.jk.miaosha.RestMiaoshaService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "config-miaosha-provider")
public interface InvokeMiaoshaAPI extends RestMiaoshaService {
}
