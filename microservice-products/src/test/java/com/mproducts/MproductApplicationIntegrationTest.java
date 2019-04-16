package com.mproducts;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MproductApplicationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void listOfProductsIT() throws Exception {
        mockMvc.perform(
                get("/Products"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":0,\"title\":\"Candle working with fire\",\"description\":\"Candle working like a bulb but without electricity\",\"image\":\"https://live.staticflickr.com/3408/3279558099_6dc30be4b6_b.jpg\",\"price\":22.0},{\"id\":1,\"title\":\"Chair to sit down\",\"description\":\"Rare chair with 4 chair legs\",\"image\":\"https://live.staticflickr.com/1012/819236264_dc25b04576_z.jpg\",\"price\":95.0},{\"id\":2,\"title\":\"Wooden horse\",\"description\":\"Wooden horse\",\"image\":\"https://live.staticflickr.com/208/466183052_0d6f7e424c_b.jpg\",\"price\":360.0}]"));
    }

    @Test
    public void listOneProductsIT() throws Exception {
        mockMvc.perform(
                get("/Products/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"title\":\"Chair to sit down\",\"description\":\"Rare chair with 4 chair legs\",\"image\":\"https://live.staticflickr.com/1012/819236264_dc25b04576_z.jpg\",\"price\":95.0}"));
    }
}
