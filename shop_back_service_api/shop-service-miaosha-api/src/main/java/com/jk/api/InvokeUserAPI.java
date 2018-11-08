package com.jk.api;

import com.jk.user.RestUserService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "config-miaosha-provider")
public interface InvokeUserAPI extends RestUserService {
}
