package com.mpayment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Payment {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private Integer idOrder;

    private Double amount;

    private Long cardNumber;

    public Payment() {
    }

    public Payment(int id, Integer idOrder, Double amount, Long cardNumber) {
        this.id = id;
        this.idOrder = idOrder;
        this.amount = amount;
        this.cardNumber = cardNumber;
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
        return "Payment{" +
                "id=" + id +
                ", idOrder=" + idOrder +
                ", amount=" + amount +
                ", cardNumber=" + cardNumber +
                '}';
    }
}
