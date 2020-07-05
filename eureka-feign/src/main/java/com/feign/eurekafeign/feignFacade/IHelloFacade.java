package com.feign.eurekafeign.feignFacade;

import com.feign.eurekafeign.component.ScheduleFacade;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "eureka-provider",fallback = ScheduleFacade.class)
public interface IHelloFacade {

    @GetMapping("/hc/hello")
    String hello();
}
