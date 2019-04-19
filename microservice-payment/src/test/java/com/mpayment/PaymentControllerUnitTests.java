package com.mpayment;

import com.mpayment.beans.OrderBean;
import com.mpayment.dao.PaymentDao;
import com.mpayment.model.Payment;
import com.mpayment.proxies.MicroserviceOrderProxy;
import com.mpayment.web.controller.PaymentController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class PaymentControllerUnitTests {

	@Mock
	private MicroserviceOrderProxy microserviceOrderProxy;
	@Mock
	private PaymentDao paymentDao;

	private PaymentController controller;
	private Payment payment;
	private OrderBean orderBean;

	@Before
	public void setUp() {
		orderBean = new OrderBean(2, 2, new Date(),1, false);

		payment = new Payment(1,20,10.0,1451254786932563L);

		MockitoAnnotations.initMocks(this);
		controller = new PaymentController(microserviceOrderProxy, paymentDao);

		// We mock the saving of the order
		Mockito.when(paymentDao.save(payment)).thenReturn(payment);
		// We mock the call to orders microservice to update the payment status of the order
		Mockito.when(microserviceOrderProxy.retrieveOneOrder(payment.getIdOrder())).thenReturn(Optional.of(orderBean));
	}

	@Test
	public void payAnOrderTest() throws Exception{
		// We test the creation of a payment (we verify that the HTTP status is 201 - Created)
		assertEquals(new ResponseEntity<Payment>(payment, HttpStatus.CREATED), controller.payAnOrder(payment));

	}

}
