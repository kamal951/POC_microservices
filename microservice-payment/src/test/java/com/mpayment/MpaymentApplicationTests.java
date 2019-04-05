package com.mpayment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.mpayment.beans.OrderBean;
import com.mpayment.dao.PaymentDao;
import com.mpayment.model.Payment;
import com.mpayment.proxies.MicroserviceOrderProxy;
import com.mpayment.web.controller.PaymentController;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.core.MediaType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class MpaymentApplicationTests {

	private PaymentController controller;

	@Mock
	private MicroserviceOrderProxy microserviceOrderProxy;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;


	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		controller = new PaymentController(microserviceOrderProxy);
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void unitTest() throws Exception{
		/*
		 *  We test the creation of a payment (we verify that the HTTP status is 201 - Created)
		 */
		Payment payment = new Payment(1,2,10.0,1451254786932563L);

		Date dt = new Date();

		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

		//Local time zone
		SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

		//Time in GMT
		dateFormatLocal.parse( dateFormatGmt.format(dt) );

		OrderBean orderBean = new OrderBean();
		orderBean.setId(2);
		orderBean.setDateOrder(dateFormatLocal.parse( dateFormatGmt.format(dt) ));
		orderBean.setOrderPayed(false);
		orderBean.setQuantity(1);
		orderBean.setProductId(null);

		Mockito.when(microserviceOrderProxy.retrieveOneOrder(2)).thenReturn(Optional.of(orderBean));

		controller.payAnOrder(payment);
//		this.mvc.perform(post("/orders")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content("{\"id\": 2,\"productId\": null,\"dateOrder\": \"2019-03-27T13:40:45.419+0000\",\"quantity\": 1,\"orderPayed\": false}"))
//				.andExpect(status().isCreated());
//
//		stubFor(get(urlEqualTo("/orders/2")).willReturn(aResponse()
//				.withHeader("Content-Type", "application/json")
//				.withBody("{\"id\": 2,\"productId\": null,\"dateOrder\": \"2019-03-27T13:40:45.419+0000\",\"quantity\": 1,\"orderPayed\": false}")));

//		this.mvc.perform(post("/payment")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(payment)))
//				.andExpect(status().isCreated());
	}

}
