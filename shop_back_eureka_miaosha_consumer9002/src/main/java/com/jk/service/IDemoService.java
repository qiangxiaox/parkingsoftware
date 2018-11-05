package com.jk.service;

import com.jk.miaosha.UserService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @ClassName : IDemoService
 * @Author : xiaoqiang
 * @Date : 2018/11/1 23:42:42
 * @Description :
 * @Version ： 1.0
 */
@FeignClient(value = "config-miaosha-provider")
public interface IDemoService extends UserService {
}
