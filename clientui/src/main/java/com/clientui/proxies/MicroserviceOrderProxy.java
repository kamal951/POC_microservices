package com.clientui.proxies;

import com.clientui.beans.OrderBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "microservice-orders")
public interface MicroserviceOrderProxy {

    @PostMapping(value = "/microservice-orders/orders")
    OrderBean addOrder(@RequestBody OrderBean order);
}
