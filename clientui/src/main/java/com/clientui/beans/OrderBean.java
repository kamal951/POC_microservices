package com.clientui.beans;

import java.util.Date;


public class OrderBean {

    private int id;

    private Integer productId;

    private Date dateOrder;

    private Integer quantity;

    private Boolean orderPayed;

    public OrderBean() {
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
        return "OrderBean{" +
                "id=" + id +
                ", productId=" + productId +
                ", dateOrder=" + dateOrder +
                ", quantity=" + quantity +
                ", orderPayed=" + orderPayed +
                '}';
    }
}
