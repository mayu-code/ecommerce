package com.main.ecommerce.entities;

import java.time.LocalDateTime;

import com.main.ecommerce.status.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class OrderPay {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long payId;

    private long userId;

    private long stackId;

    private int paidAmmount;

    private OrderStatus payStatus = OrderStatus.PENDING;

    private LocalDateTime payDate = LocalDateTime.now();

    private String transitionId;

}
