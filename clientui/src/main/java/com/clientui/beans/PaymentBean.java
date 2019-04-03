package com.clientui.beans;


public class PaymentBean {

    private int id;

    private Integer idOrder;

    private Double amount;

    private Long cardNumber;

    public PaymentBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "PaymentBean{" +
                "id=" + id +
                ", idOrder=" + idOrder +
                ", amount=" + amount +
                ", cardNumber=" + cardNumber +
                '}';
    }
}
