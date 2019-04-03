package com.morders;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.morders.model.MyOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MordersApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void contextLoads() {
	}

	@Test
	public void unitTest()  throws Exception{
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
		MyOrder cmd = new MyOrder(1,1,dateFormatLocal.parse( dateFormatGmt.format(dt) ),1,true);

		// POST request to /orders with the order we want to update
		this.mvc.perform(post("/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cmd)))
				.andExpect(status().isCreated());


		/*
		*  We check that the order has been created with a GET request
		*/

		// GET request to /orders
		this.mvc.perform(get("/orders")).andExpect(status().isOk())
				.andExpect(content().string("[{\"id\":1,\"productId\":1,\"dateOrder\":\""+dateFormatGmt.format(dt)+"\",\"quantity\":1,\"orderPayed\":true}]"));

		/*
		*  We test the GET request for one order
		*/

		// GET request to /orders/1
		this.mvc.perform(get("/orders/1")).andExpect(status().isOk())
				.andExpect(content().string("{\"id\":1,\"productId\":1,\"dateOrder\":\""+dateFormatGmt.format(dt)+"\",\"quantity\":1,\"orderPayed\":true}"));

		/*
		*  We check that we have the rigth HTTP status when an order doesn't exist
		*/

		// GET request to /orders/10
		this.mvc.perform(get("/orders/10")).andExpect(status().isNotFound());

		/*
		*  We test the update of an order
		*/

		// MyOrder to update
		MyOrder cmd2 = new MyOrder(1,1,dateFormatLocal.parse( dateFormatGmt.format(dt) ),3,true);

		// PUT request to /orders with the order we want to update
		this.mvc.perform(put("/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cmd2)))
				.andExpect(status().isOk());

		/*
		*  We check that the order has been modified with a GET request
		*/

		// GET request to /orders
		this.mvc.perform(get("/orders")).andExpect(status().isOk())
				.andExpect(content().string("[{\"id\":1,\"productId\":1,\"dateOrder\":\""+dateFormatGmt.format(dt)+"\",\"quantity\":3,\"orderPayed\":true}]"));
	}

}
