package com.mpayment.dao;

import com.mpayment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDao extends JpaRepository<Payment, Integer>{

    Payment findByidOrder(int idOrder);
}
