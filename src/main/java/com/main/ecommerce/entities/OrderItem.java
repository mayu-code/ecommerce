package com.main.ecommerce.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long itemId;

    // @ManyToOne
    // @JoinColumn(name = "order_id", nullable = false)
    // private Order order;

    @ManyToOne
    private Product product;

    private Integer quantity;

    private int price;

    private LocalDateTime addedDate = LocalDateTime.now();


    @ManyToOne
    @JsonIgnore
    private OrderStack stack;

}
