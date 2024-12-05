package com.main.ecommerce.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String brand;
	private String name;
	private int price;
	private String category;
	private String subcategory;
	private String features;
	private String imgUrl;
	private LocalDateTime addedDate;
	private LocalDateTime deletedDate;
	private boolean isEnable;

	@ManyToOne
	@JoinColumn(name = "admin_id")
	private Admin admin;
	
}
