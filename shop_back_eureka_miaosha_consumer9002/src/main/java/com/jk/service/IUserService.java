package com.jk.service;

import com.jk.user.RestUserService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "config-user-provider")
public interface IUserService extends RestUserService {
}
