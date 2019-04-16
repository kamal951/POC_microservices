package com.clientui;

import com.clientui.controller.ClientController;
import com.clientui.proxies.MicroserviceOrderProxy;
import com.clientui.proxies.MicroservicePaymentProxy;
import com.clientui.proxies.MicroserviceProductsProxy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClientUiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private ClientController clientController;
	private MicroserviceProductsProxy microserviceProductsProxy;
	private MicroservicePaymentProxy microservicePaymentProxy;
	private MicroserviceOrderProxy microserviceOrderProxy;

	@Before
	public void setUp() throws Exception {
		clientController = new ClientController(microserviceProductsProxy, microserviceOrderProxy, microservicePaymentProxy);
	}

	//	@Before
//	public void setUp(){
//		clientController = new ClientController();
//	}

}
