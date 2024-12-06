package com.main.ecommerce.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.main.ecommerce.entities.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    
	private String name;
	private String email;
	private String mobileNo;
	private LocalDateTime loginDate;
	private List<Long> myOrders = new ArrayList<>();
	private List<Long> myCart = new ArrayList<>();
    private List<Address> addresses = new ArrayList<>();
}
