package com.feign.eurekafeign.component;

import com.feign.eurekafeign.feignFacade.IHelloFacade;
import org.springframework.stereotype.Component;

@Component
public class ScheduleFacade implements IHelloFacade {


    @Override
    public String hello() {
        return "sorry";
    }
}
