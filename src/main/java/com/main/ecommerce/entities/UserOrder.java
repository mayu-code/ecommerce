package com.main.ecommerce.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.main.ecommerce.payment.PaymentMethod;
import com.main.ecommerce.status.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class UserOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @OneToOne(mappedBy = "userOrder", fetch = FetchType.LAZY)
    @JsonIgnore
    private OrderStack orderStack;

    private OrderStatus orderStatus = OrderStatus.PENDING;

    private LocalDateTime orderDate = LocalDateTime.now();

    private PaymentMethod paymentMethod;

    private String shippingAddress;

    private int totalPaid;

    private String transitionId;

    @ManyToOne
    @JsonIgnore
    private User user;

}
