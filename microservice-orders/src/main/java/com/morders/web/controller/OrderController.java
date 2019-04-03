package com.morders.web.controller;


import com.morders.dao.OrdersDao;
import com.morders.model.MyOrder;
import com.morders.web.exceptions.OrderNotFoundException;
import com.morders.web.exceptions.ImpossibleToAddOrderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {

    @Autowired
    OrdersDao ordersDao;

    /*
     * We configure a POST request to add an order
    */
    @PostMapping (value = "/orders")
    public ResponseEntity<MyOrder> addOrder(@RequestBody MyOrder order){
        MyOrder newOrder = ordersDao.save(order);
        if(newOrder == null) throw new ImpossibleToAddOrderException("Impossible to add this order");
        return new ResponseEntity<MyOrder>(order, HttpStatus.CREATED);
    }

    /*
     * We configure a GET request to retrieve all orders
    */
    @GetMapping(value = "/orders")
    public List<MyOrder> retrieveAllOrders(){
        List<MyOrder> ordersList = ordersDao.findAll();
        return ordersList;
    }

    /*
     * We configure a GET request to retirieve one order with its id
    */
    @GetMapping(value = "/orders/{id}")
    public Optional<MyOrder> retrieveOneOrder(@PathVariable int id){

        Optional<MyOrder> order = ordersDao.findById(id);

        if(!order.isPresent()) throw new OrderNotFoundException("This order doesn't exist!");

        return order;
    }

    /*
    * We configure a PUT request to update an order
    **/
    @PutMapping(value = "/orders")
    public void updateOrder(@RequestBody MyOrder order) {
        ordersDao.save(order);
    }
}
