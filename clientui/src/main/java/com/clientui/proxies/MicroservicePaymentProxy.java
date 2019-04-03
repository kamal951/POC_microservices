package com.clientui.proxies;

import com.clientui.beans.PaymentBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "microservice-payment")
public interface MicroservicePaymentProxy {

    @PostMapping(value = "/microservice-payment/payment")
    ResponseEntity<PaymentBean> payOrder(@RequestBody PaymentBean payment);

}
