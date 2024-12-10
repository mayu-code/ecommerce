package com.main.ecommerce.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.main.ecommerce.status.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class OrderStack {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long stackId;

    @OneToMany(mappedBy = "stack", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> Mycart = new ArrayList<>();

    private int totalPrice = 0;

    private OrderStatus status = OrderStatus.PENDING;

    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToOne
    @JsonIgnore
    private UserOrder userOrder;

}
