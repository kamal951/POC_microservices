package com.morders.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/*
*  This class is named MyOrder because the h2 database will return errors if we call it Order (it cannot do the difference with the "order" keyword)
* */
@Entity
public class MyOrder {

    @Id
    @GeneratedValue
    private int id;

    private Integer productId;

    private Date dateOrder;

    private Integer quantity;

    private Boolean orderPayed;

    public MyOrder() {
    }

    public MyOrder(int id, Integer productId, Date dateOrder, Integer quantity, Boolean orderPayed) {
        this.id = id;
        this.productId = productId;
        this.dateOrder = dateOrder;
        this.quantity = quantity;
        this.orderPayed = orderPayed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getOrderPayed() {
        return orderPayed;
    }

    public void setOrderPayed(Boolean orderPayed) {
        this.orderPayed = orderPayed;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

        //Local time zone
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        //Time in GMT
        try{
            dateFormatLocal.parse( dateFormatGmt.format(dateOrder));
        }catch(Exception e){
            System.err.println(e);
        }

        try{
            return "order{" +
                    "id=" + id +
                    ", productId=" + productId +
                    ", dateOrder=" +  dateFormatLocal.parse( dateFormatGmt.format(dateOrder)) +
                    ", quantity=" + quantity +
                    ", orderPayed=" + orderPayed +
                    '}';
        }catch(Exception e){
            return null;
        }

    }
}
