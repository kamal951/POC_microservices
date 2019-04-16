package com.clientui.controller;

import com.clientui.beans.OrderBean;
import com.clientui.beans.PaymentBean;
import com.clientui.beans.ProductBean;
import com.clientui.proxies.MicroserviceOrderProxy;
import com.clientui.proxies.MicroservicePaymentProxy;
import com.clientui.proxies.MicroserviceProductsProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


@Controller
public class ClientController {

    private MicroserviceProductsProxy productsProxy;
    private MicroserviceOrderProxy orderProxy;
    private MicroservicePaymentProxy paymentProxy;

    public ClientController(MicroserviceProductsProxy productsProxy, MicroserviceOrderProxy orderProxy, MicroservicePaymentProxy paymentProxy) {
        this.productsProxy = productsProxy;
        this.orderProxy = orderProxy;
        this.paymentProxy = paymentProxy;
    }

    Logger log = LoggerFactory.getLogger(this.getClass());

    /*
    *  Step (1)
    *  We get the list of the products and we display them on the home page.
    *  The products are retrieved with ProductsProxy
    *  We return the home page to which we have passed the list of products in parameter
    * */
    @RequestMapping("/")
    public String home(Model model){

        log.info("Send request to microservice-products");

        List<ProductBean> products =  productsProxy.listOfProducts();

        model.addAttribute("products", products);

        return "Home";
    }

    /*
    *  Step (2)
    *  We retrieve the detail of a product
    *  We pass the "product" object who contains the details of that product to the ProductDetail page
    * */
    @RequestMapping("/details-products/{id}")
    public String productDetails(@PathVariable int id,  Model model){

        ProductBean product = productsProxy.retrieveOneProduct(id);

        model.addAttribute("product", product);

        return "ProductDetail";
    }

    /*
    *  Steps (3) and (4)
    *  We call orders microservice to create an order and the details of that order
    * */
    @RequestMapping(value = "/order-product/{idProduct}/{amount}")
    public String passOrder(@PathVariable int idProduct, @PathVariable Double amount,  Model model){


        OrderBean order = new OrderBean();

        // We set the properties of the OrderBean object that we created
        order.setProductId(idProduct);
        order.setQuantity(1);
        order.setDateOrder(new Date());


        // We call orders microservice with Feign and we get the details of the created order, especially its ID (step 4)
        OrderBean orderAdded = orderProxy.addOrder(order);

        // We pass the object order to the view and its amount to have the needed data for the payment
        model.addAttribute("order", orderAdded);
        model.addAttribute("amount", amount);

        return "Payment";
    }

    /*
    *  Step (5)
    *  We call payment microservice to process payment
    * */
    @RequestMapping(value = "/pay-order/{idOrder}/{amountOrder}")
    public String payOrder(@PathVariable int idOrder, @PathVariable Double amountOrder, Model model){

        PaymentBean paymentToExcecute = new PaymentBean();

        // We set the details of the payment
        paymentToExcecute.setIdOrder(idOrder);
        paymentToExcecute.setAmount(amountOrder);
        paymentToExcecute.setCardNumber(cardnum()); // We generate a fake bank card number


        // We call payment microservice and (step 7) we get the result
        ResponseEntity<PaymentBean> payment = paymentProxy.payOrder(paymentToExcecute);

        Boolean paymentAccepted = false;

        // If we have anothe status code than 201 - CREATED, it means that the payment haven't succeed
        if(payment.getStatusCode() == HttpStatus.CREATED)
                paymentAccepted = true;

        model.addAttribute("paymentOk", paymentAccepted); // We send a Boolean paymentOK to the view

        model.addAttribute("idOrder", idOrder);

        return "confirmation";
    }


    // Generate 16 random numbers to simulate vaguely a bank card
    private Long cardnum() {

        return ThreadLocalRandom.current().nextLong(1000000000000000L,9000000000000000L );
    }
}
