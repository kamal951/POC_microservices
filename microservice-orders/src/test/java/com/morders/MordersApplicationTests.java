package com.morders;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.morders.model.MyOrder;
import com.morders.web.controller.OrderController;
import com.morders.web.exceptions.OrderNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MordersApplicationTests {

	@Autowired
	private OrderController orderController;

	@Test
	public void contextLoads() {
	}

	@Test
	public void addOrderTest()  throws Exception{

		/*
		* Prepare the order object to test the function
		* */
		Date dt = new Date();

		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

		//Local time zone
		SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

		//Time in GMT
		dateFormatLocal.parse( dateFormatGmt.format(dt) );

		/*
		*  We test the creation of an order (we verify that the HTTP status is 201 - Created)
		*/
		// MyOrder to create
		MyOrder order = new MyOrder(1,1,dateFormatLocal.parse( dateFormatGmt.format(dt) ),1,true);


		// POST request to /orders with the order we want to update
		Assert.assertEquals(orderController.addOrder(order), new ResponseEntity<MyOrder>(order, HttpStatus.CREATED));

	}


	@Test
	public void getOrderTest() throws Exception{


		/*
		 * Prepare the order object to test the function
		 * */
		Date dt = new Date();

		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

		//Local time zone
		SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

		//Time in GMT
		dateFormatLocal.parse( dateFormatGmt.format(dt) );


		MyOrder order = new MyOrder(1,1,dateFormatLocal.parse( dateFormatGmt.format(dt) ),1,true);

		// We create the order to test if we can get it
		orderController.addOrder(order);
		List<MyOrder> list = new ArrayList<MyOrder>();
		list.add(order);
		// Assertion
		Assert.assertEquals(orderController.retrieveAllOrders().toString(), list.toString());


	}

	@Test
	public void getOneOrderTest() throws Exception{
		/*
		 *  We test the GET request for one order
		 */

		/*
		 * Prepare the order object to test the function
		 * */
		Date dt = new Date();

		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

		//Local time zone
		SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

		//Time in GMT
		dateFormatLocal.parse( dateFormatGmt.format(dt) );


		MyOrder order = new MyOrder(1,1,dateFormatLocal.parse( dateFormatGmt.format(dt) ),1,true);

		// We create the order to test if we can get it
		orderController.addOrder(order);

		// Assertion
		Assert.assertEquals(orderController.retrieveOneOrder(1).get().toString(), order.toString());

	}

	@Test(expected= OrderNotFoundException.class)
	public void getOneOrderErrorTest(){
		/*
		 *  We check that we have the rigth HTTP status when an order doesn't exist
		 */

		orderController.retrieveOneOrder(10);

	}

	@Test
	public void updateOrderTest() throws Exception{
		/*
		 *  We test the update of an order
		 */

		/*
		 * Prepare the order object to test the function
		 * */
		Date dt = new Date();

		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

		//Local time zone
		SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

		//Time in GMT
		dateFormatLocal.parse( dateFormatGmt.format(dt) );


		MyOrder order = new MyOrder(1,1,dateFormatLocal.parse( dateFormatGmt.format(dt) ),1,true);

		// We create the order to test if we can get it
		orderController.addOrder(order);

		MyOrder updatedOrder = new MyOrder(1,1,dateFormatLocal.parse( dateFormatGmt.format(dt) ),15,true);

		orderController.updateOrder(updatedOrder);

		Assert.assertEquals(orderController.retrieveOneOrder(1).get().toString(), updatedOrder.toString());

		// PUT request to /orders with the order we want to update
//		this.mvc.perform(put("/orders")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(cmd2)))
//				.andExpect(status().isOk());
	}

}
