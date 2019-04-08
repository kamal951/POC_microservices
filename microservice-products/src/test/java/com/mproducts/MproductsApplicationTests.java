package com.mproducts;

import com.mproducts.model.Product;
import com.mproducts.web.controller.ProductController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MproductsApplicationTests {

	@Autowired
	private ProductController productCtrler;

	@Test
	public void contextLoads() {
	}

	/*
	*  Unit Tests
	* */
	@Test
	public void listOfProductsTest(){
		// Preparing expected list
		Product candle = new Product(0,"Candle working with fire", "Candle working like a bulb but without electricity", "https://live.staticflickr.com/3408/3279558099_6dc30be4b6_b.jpg", 22.0);
		Product chair = new Product(1,"Chair to sit down", "Rare chair with 4 chair legs", "https://live.staticflickr.com/1012/819236264_dc25b04576_z.jpg", 95.0);
		Product horse = new Product(2,"Wooden horse", "Wooden horse", "https://live.staticflickr.com/208/466183052_0d6f7e424c_b.jpg", 360.0);
		List<Product> expectedList = new ArrayList();
		expectedList.add(candle);
		expectedList.add(chair);
		expectedList.add(horse);

		// Testing list of all products method
		List<Product> list = productCtrler.listOfProducts();
		Assert.assertEquals(list.toString(), expectedList.toString());
	}


	@Test
	public void getOneProductTest(){
		// Preparing expected list
		Product chair = new Product(1,"Chair to sit down", "Rare chair with 4 chair legs", "file:///images/chair.jpg", 95.0);
		List<Product> expectedList = new ArrayList();
		expectedList.add(chair);

		// Testing get one product method
		Optional<Product> prdct = productCtrler.getOneProduct(1);
		Assert.assertEquals(prdct.get().toString(), chair.toString());
	}

}
