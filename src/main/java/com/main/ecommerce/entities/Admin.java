package com.main.ecommerce.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    
    private long id;
    private String userName;
    private String password;
    
    private List<Product> myProducts = new ArrayList<>();

}
