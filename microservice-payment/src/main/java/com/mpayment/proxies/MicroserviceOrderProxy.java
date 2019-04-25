package com.mpayment.proxies;

import com.mpayment.beans.OrderBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "microservice-orders", url = "10.126.226.2:9002")
public interface MicroserviceOrderProxy {

    @GetMapping(value = "/orders/{id}")
    Optional<OrderBean> retrieveOneOrder(@PathVariable("id") int id);

    @PutMapping(value = "/orders")
    void updateOrder(@RequestBody OrderBean order);
}
