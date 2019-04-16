package com.mpayment;

import com.mpayment.beans.OrderBean;
import com.mpayment.dao.PaymentDao;
import com.mpayment.proxies.MicroserviceOrderProxy;
import com.mpayment.web.controller.PaymentController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MpaymentApplicationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private PaymentController controller;

    @Mock
    private MicroserviceOrderProxy microserviceOrderProxy;

    @Autowired
    private PaymentDao paymentDao;

    private OrderBean orderBean;

    @Before
    public void setUp() {
        // We create an order to mock the returned response of orders microservice
        orderBean = new OrderBean(1,1, new Date(), 1, false);

        // We instantiate our mocks
        MockitoAnnotations.initMocks(this);

        // We give our mocks to the controller in parameter so it doesn't use the feign client
        controller = new PaymentController(microserviceOrderProxy, paymentDao);

        // We build mockmvc with the controller so it don't take the default one
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // We mock the call to orders microservice when we want to retrieve an order
        Mockito.when(microserviceOrderProxy.retrieveOneOrder(1)).thenReturn(Optional.of(orderBean));

    }

    @Test
    public void payAnOrderIT() throws Exception {
        // Test and see the result (HTTP status 201 if success)
        mockMvc.perform(post("/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1, \"idOrder\": 1, \"amount\": 10.0, \"cardNumber\": 1451254786932563}"))
                .andExpect(status().isCreated());
    }

}