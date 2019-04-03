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
	ProductController productCtrler = new ProductController();

	@Test
	public void contextLoads() {
	}

	@Test
	public void unitTestProducts(){
		// Preparing expected list
		Product candle = new Product(0,"Candle working with fire", "Candle working like a bulb but without electricity", "file:///images/candle.jpg", 22.0);
		Product chair = new Product(1,"Chair to sit down", "Rare chair with 4 chair legs", "file:///images/chair.jpg", 95.0);
		Product horse = new Product(2,"Wooden horse", "Wooden horse", "file:///images/horse.jpg", 360.0);
		List<Product> expectedList = new ArrayList();
		expectedList.add(candle);
		expectedList.add(chair);
		expectedList.add(horse);

		// Testing list of all products method
		List<Product> list = productCtrler.listOfProducts();
		Assert.assertEquals(list.toString(), expectedList.toString());

		// Testing get one product method
		Optional<Product> prdct = productCtrler.getOneProduct(1);
		Assert.assertEquals(prdct.get().toString(), chair.toString());
	}

}
