package com.morders;

import com.morders.dao.OrdersDao;
import com.morders.model.MyOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDaoTest {

    @Autowired
    private OrdersDao ordersDao;

    private MyOrder order;

    @Before
    public void setup() {
        this.order = saveMyOrder();
    }

    private MyOrder saveMyOrder() {
        MyOrder customer = new MyOrder(1, 1, new Date(), 1, false);
        MyOrder savedMyOrder = ordersDao.save(customer);
        Assert.assertNotNull(savedMyOrder);
        return savedMyOrder;
    }


    @Test
    public void findByIdTest(){
        Optional<MyOrder> order2 = ordersDao.findById(1);
        Assert.assertEquals(order.getId(), order2.get().getId());
    }

    @Test
    public void findAllTest(){
        List<MyOrder> list = ordersDao.findAll();
        List<MyOrder> expectedList = new ArrayList<MyOrder>();
        expectedList.add(order);

        Assert.assertEquals(expectedList.get(0).toString(), list.get(0).toString());
    }

}
