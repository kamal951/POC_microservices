package com.mpayment.web.controller;

import com.mpayment.beans.OrderBean;
import com.mpayment.dao.PaymentDao;
import com.mpayment.model.Payment;
import com.mpayment.proxies.MicroserviceOrderProxy;
import com.mpayment.web.exceptions.ExistingPaymentException;
import com.mpayment.web.exceptions.ImpossiblePaymentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PaymentController {

    @Autowired
    private PaymentDao paymentDao;


    private MicroserviceOrderProxy microserviceOrderProxy;

    public PaymentController(MicroserviceOrderProxy microserviceOrderProxy) {
        this.microserviceOrderProxy = microserviceOrderProxy;
    }

    /*
    *  Operations to save a payment and notify the orders microservice to update the status of the sayed oreder
    **/
    @PostMapping(value = "/payment")
    public ResponseEntity<Payment>  payAnOrder(@RequestBody Payment payment){

        // We verify if the order has been already payed
        System.out.println("We verify if the order has been already payed");
        System.out.println(paymentDao);
        Payment existingPayment = paymentDao.findByidOrder(payment.getIdOrder());
        System.out.println("exist payment ?");
        System.out.println(existingPayment != null);
        if(existingPayment != null) throw new ExistingPaymentException("This order has already been payed!");

        // We save the payment
        System.out.println("We save the payment");
        Payment newPayment = paymentDao.save(payment);

        // if the DAO return null, there was a problem when saving the payment
        System.out.println("if the DAO return null, there was a problem when saving the payment");
        if(newPayment == null) throw new ImpossiblePaymentException("Error, impossible to establish the payment, retry later!");

        // We retrieve the order corresponding to that payment by calling orders microservice
        System.out.println("We retrieve the order corresponding to that payment by calling orders microservice");
        Optional<OrderBean> orderReq = microserviceOrderProxy.retrieveOneOrder(payment.getIdOrder());

        // orderReq.get() extract the object of type OrderBean from Optional
        System.out.println("orderReq.get() extract the object of type OrderBean from Optional");
        OrderBean order = orderReq.get();

        // We update the object to mak the order as payed
        System.out.println("We update the object to mak the order as payed");
        order.setOrderPayed(true);

        // We send the object updated to the orders microservice to update the order's status
        System.out.println("We send the object updated to the orders microservice to update the order's status");
        microserviceOrderProxy.updateOrder(order);

        // We return 201 CREATED to notify the client that the payment has been registered
        System.out.println("We return 201 CREATED to notify the client that the payment has been registered");
        return new ResponseEntity<Payment>(newPayment, HttpStatus.CREATED);

    }

}
