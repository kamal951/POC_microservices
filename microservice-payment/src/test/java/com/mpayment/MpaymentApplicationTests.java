package com.mpayment;

import com.mpayment.beans.OrderBean;
import com.mpayment.dao.PaymentDao;
import com.mpayment.model.Payment;
import com.mpayment.proxies.MicroserviceOrderProxy;
import com.mpayment.web.controller.PaymentController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class MpaymentApplicationTests {

	@Mock
	private MicroserviceOrderProxy microserviceOrderProxy;

	@Mock
	private PaymentDao paymentDao;

	private PaymentController controller;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		controller = new PaymentController(microserviceOrderProxy, paymentDao);
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void payAnOrderTest() throws Exception{
		/*
		 *  We test the creation of a payment (we verify that the HTTP status is 201 - Created)
		 */

		// We create a fake payment and order
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

		Payment payment = new Payment(1,20,10.0,1451254786932563L);

		// We mock the saving of the order
		Mockito.when(paymentDao.save(payment)).thenReturn(payment);

		// We mock the call to orders microservice to update the payment status of the order
		Mockito.when(microserviceOrderProxy.retrieveOneOrder(payment.getIdOrder())).thenReturn(Optional.of(orderBean));

		// We call the method to test it
		assertEquals(new ResponseEntity<Payment>(payment, HttpStatus.CREATED), controller.payAnOrder(payment));

	}

}
