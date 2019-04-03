package com.mproducts.web.controller;

import com.mproducts.configurations.ApplicationPropertiesConfiguration;
import com.mproducts.dao.ProductDao;
import com.mproducts.model.Product;
import com.mproducts.web.exceptions.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController implements HealthIndicator {

    @Autowired
    ProductDao productDao;


    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ApplicationPropertiesConfiguration appProperties;

    @Override
    public Health health() {

        List<Product> products = productDao.findAll();

        if(products.isEmpty()) {
            return Health.down().build();
        }
        return Health.up().build();
    }

    // Retrieve the list of all products
    @GetMapping(value = "/Products")
    public List<Product> listOfProducts(){

        List<Product> products = productDao.findAll();

        if(products.isEmpty()) throw new ProductNotFoundException("No products to sell!");

        List<Product> limitedList = products.subList(0, appProperties.getLimitOfProducts());

        log.info("Retrieving list of products");

        return products;

    }

    // Retrieve a product with its id
    @GetMapping( value = "/Products/{id}")
    public Optional<Product> getOneProduct(@PathVariable int id) {

        Optional<Product> product = productDao.findById(id);

        if(!product.isPresent())  throw new ProductNotFoundException("The product with id " + id + " doesn't exist!");

        return product;
    }
}
