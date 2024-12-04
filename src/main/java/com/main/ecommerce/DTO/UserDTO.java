package com.main.ecommerce.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    
    private long id;
    private String name;
	private String email;
	private String mobileNo;
	private List<Long> myOrders = new ArrayList<>();
	private List<Long> myCart = new ArrayList<>();

}
