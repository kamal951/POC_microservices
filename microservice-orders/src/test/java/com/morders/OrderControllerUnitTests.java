package com.morders;

import com.morders.dao.OrdersDao;
import com.morders.model.MyOrder;
import com.morders.web.controller.OrderController;
import com.morders.web.exceptions.ImpossibleToAddOrderException;
import com.morders.web.exceptions.OrderNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

public class OrderControllerUnitTests {

	private OrderController orderController;
	private OrdersDao ordersDao;
	private MyOrder order;
	private List<MyOrder> list = new ArrayList<MyOrder>();

	@Before
	public void setUp() {
		// We create an order to return we call findById and save
		order = new MyOrder(1,1,new Date(), 1, false);
		// We add the order to a list to return the list when we call findAll
		list.add(order);

		// We mock OrdersDao class
		ordersDao = Mockito.mock(OrdersDao.class);
		// We instantiate the controller we our mock
		orderController = new OrderController(ordersDao);

		//Methods to mock
		Mockito.when(ordersDao.findAll()).thenReturn(list);
		Mockito.when(ordersDao.findById(1)).thenReturn(Optional.of(order));
		Mockito.when(ordersDao.save(order)).thenReturn(order);
	}

	@Test
	public void addOrderTest(){
		// Assert that when we call the method, we have the expected result
		Assert.assertEquals(orderController.addOrder(order), new ResponseEntity<MyOrder>(order, HttpStatus.CREATED));
	}

	@Test
	public void getOrderTest(){
		// Assertion
		Assert.assertEquals(orderController.retrieveAllOrders().toString(), list.toString());
	}

	//We test the GET request for one order
	@Test
	public void getOneOrderTest(){
		// Assertion
		Assert.assertEquals(orderController.retrieveOneOrder(1).get().toString(), order.toString());
	}

	//We test the update of an order
	@Test
	public void updateOrderTest(){
		// We create the order to test if we can get it
		orderController.updateOrder(order);
	}

	@Test(expected= ImpossibleToAddOrderException.class)
	public void addOrderFailTest(){
		// We simulate a null order
		orderController.addOrder(null);
	}

	// We check that we have the right exception when an order doesn't exist
	@Test(expected= OrderNotFoundException.class)
	public void getOneOrderFailTest(){
		orderController.retrieveOneOrder(10);
	}

}
