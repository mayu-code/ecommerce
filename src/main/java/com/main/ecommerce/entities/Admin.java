package com.main.ecommerce.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
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
    private String role;

    @JsonIgnore
    @OneToMany(mappedBy = "admin",fetch = FetchType.LAZY , orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Product> myProducts = new ArrayList<>();

}
