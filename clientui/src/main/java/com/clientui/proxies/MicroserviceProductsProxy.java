package com.clientui.proxies;

import com.clientui.beans.ProductBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "microservice-products")
public interface MicroserviceProductsProxy {

    @GetMapping(value = "/microservice-products/Products")
    List<ProductBean> listOfProducts();

    /*
    *  Note that the annontaion @PathVariable("id") is different than the one used in the controller
    **/
    @GetMapping( value = "/microservice-products/Products/{id}")
    ProductBean retrieveOneProduct(@PathVariable("id") int id);

}
