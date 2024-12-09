package com.main.ecommerce.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.main.ecommerce.status.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderId;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    private Double totalAmmount;

    // @Column(nullable = false)
    // private String deliverAddress;

    private OrderStatus status;

    private OrderPayment payment;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

}
