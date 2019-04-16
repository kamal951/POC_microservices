package com.mproducts;

import com.mproducts.model.Product;
import com.mproducts.web.controller.ProductController;
import com.mproducts.web.exceptions.ProductNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllerUnitTests {

	@Autowired
	private ProductController productCtrler;

	// test the method that get the list of all products
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

		Assert.assertEquals(productCtrler.listOfProducts().toString(), expectedList.toString());
	}


	/*
	 *  Unit Tests
	 */
	@Test
	public void getOneProductTest(){
		// Preparing expected list
		Product chair = new Product(1,"Chair to sit down", "Rare chair with 4 chair legs", "https://live.staticflickr.com/1012/819236264_dc25b04576_z.jpg", 95.0);
		List<Product> expectedList = new ArrayList();
		expectedList.add(chair);

		// Testing get one product method
		Optional<Product> prdct = productCtrler.getOneProduct(1);
		Assert.assertEquals(prdct.get().toString(), chair.toString());
	}

	@Test(expected = ProductNotFoundException.class)
	public void getOneProductFailTest(){
		// Testing get one product method to fail
		productCtrler.getOneProduct(10);
	}


}
