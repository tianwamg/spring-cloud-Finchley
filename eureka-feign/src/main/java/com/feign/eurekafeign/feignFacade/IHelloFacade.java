package com.feign.eurekafeign.feignFacade;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "eureka-provider")
public interface IHelloFacade {

    @GetMapping("/hc/hello")
    String hello();
}
