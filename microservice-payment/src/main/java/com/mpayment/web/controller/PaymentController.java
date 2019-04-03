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

    @Autowired
    private MicroserviceOrderProxy microserviceOrderProxy;

    /*
    *  Operations to save a payment and notify the orders microservice to update the status of the sayed oreder
    **/
    @PostMapping(value = "/payment")
    public ResponseEntity<Payment>  payAnOrder(@RequestBody Payment payment){

        // We verify if theorder has been already payed
        Payment existingPayment = paymentDao.findByidOrder(payment.getIdOrder());
        if(existingPayment != null) throw new ExistingPaymentException("This order has already been payed!");

        // We save the payment
        Payment newPayment = paymentDao.save(payment);

        // if the DAO return null, there was a problem when saving the payment
        if(newPayment == null) throw new ImpossiblePaymentException("Error, impossible to establish the payment, retry later!");

        // We retrieve the order corresponding to that payment by calling orders microservice
        Optional<OrderBean> orderReq = microserviceOrderProxy.retrieveOneOrder(payment.getIdOrder());

        // orderReq.get() extract the object of type OrderBean from Optional
        OrderBean order = orderReq.get();

        // We update the object to mak the order as payed
        order.setOrderPayed(true);

        // We send the object updated to the orders microservice to update the order's status
        microserviceOrderProxy.updateOrder(order);

        // We return 201 CREATED to notify the client that the payment has been registered
        return new ResponseEntity<Payment>(newPayment, HttpStatus.CREATED);

    }

}
