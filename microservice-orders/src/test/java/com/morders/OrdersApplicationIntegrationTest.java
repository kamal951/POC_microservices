package com.morders;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.core.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrdersApplicationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addOrderIT() throws Exception {
        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1,\"dateOrder\": \"2019-04-11T14:38:25.555\",\"quantity\": 1,\"orderPayed\": false}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void retrieveAllOrdersIT() throws Exception {
        mockMvc.perform(
                get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    public void retrieveOneOrdersIT() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    public void updateOrderIT() throws Exception {
        mockMvc.perform(put("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1,\"dateOrder\": \"2019-04-11T14:38:25.555\",\"quantity\": 1,\"orderPayed\": false}"))
                .andExpect(status().isOk());
    }
}
