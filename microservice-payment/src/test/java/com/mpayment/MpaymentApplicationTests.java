package com.mpayment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpayment.model.Payment;
import com.mpayment.web.controller.PaymentController;
import io.specto.hoverfly.junit.core.SimulationSource;
import io.specto.hoverfly.junit.dsl.HoverflyDsl;
import io.specto.hoverfly.junit.dsl.HttpBodyConverter;
import io.specto.hoverfly.junit.dsl.ResponseCreators;
import io.specto.hoverfly.junit.rule.HoverflyRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class MpaymentApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PaymentController paymentController;

//	@Autowired
//	private RestTemplate restTemplate;


	@Test
	public void contextLoads() {
	}

//	public static final HoverflyRule rule
//			= HoverflyRule.inSimulationMode(SimulationSource.dsl(
//			HoverflyDsl.service("http://localhost:9002")
//					.get("/orders/2")
//					.willReturn(ResponseCreators.success().body(
//							HttpBodyConverter.jsonWithSingleQuotes("{\"id\": 2,\"productId\": null,\"dateOrder\": \"2019-03-27T13:40:45.419+0000\",\"quantity\": 1,\"orderPayed\": false}")))));

	@Test
	public void unitTest() throws Exception{
		/*
		 *  We test the creation of a payment (we verify that the HTTP status is 201 - Created)
		 */
		Payment payment = new Payment(1,2,10.0,1451254786932563L);
//
//		System.out.println(paymentController.payAnOrder(payment));

		// POST request to /orders with the order we want to update

		this.mvc.perform(post("/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\": 2,\"productId\": null,\"dateOrder\": \"2019-03-27T13:40:45.419+0000\",\"quantity\": 1,\"orderPayed\": false}"))
				.andExpect(status().isCreated());
//
		this.mvc.perform(post("/payment")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(payment)))
				.andExpect(status().isCreated());
	}

}
