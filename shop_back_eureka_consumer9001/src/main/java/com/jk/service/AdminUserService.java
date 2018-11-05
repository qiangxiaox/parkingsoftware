package com.jk.service;

import com.jk.service.auth.IAdminUserService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "config-eureka-provider")
public interface AdminUserService extends IAdminUserService {
}
