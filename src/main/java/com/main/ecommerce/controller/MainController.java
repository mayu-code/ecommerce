package com.main.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.ecommerce.ResponseEntity.DataResponse;
import com.main.ecommerce.entities.Product;
import com.main.ecommerce.services.impl.ProductServiceImpl;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/home")
@CrossOrigin(origins = { "http://localhost:5173/", "http://localhost:5174/" })
public class MainController {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @GetMapping("/allProduct")
    public ResponseEntity<DataResponse> allProducts1() {
        List<Product> products = new ArrayList<>();

        DataResponse response = new DataResponse();

        try {
            products = productServiceImpl.allProducts();
            response.setStatus(HttpStatus.OK);
            response.setMessage("Products Fetched Successfully");
            response.setData(products);

            return ResponseEntity.of(Optional.of(response));
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Products Fetched failed");
            response.setData(products);

            return ResponseEntity.of(Optional.of(response));
        }
    }

    @GetMapping("Product/{id}")
    public ResponseEntity<DataResponse> getProductByid(@PathVariable("id") long id){
        DataResponse data = new DataResponse();
        Product product = new Product();
        try{

            product = this.productServiceImpl.getProductbyId(id);
            data.setData(product);
            data.setMessage("product get successfully");
            data.setStatus(HttpStatus.OK);
            return ResponseEntity.of(Optional.of(data));

        }catch(Exception e){
            data.setData(null);
            data.setMessage("error found !");
            data.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return ResponseEntity.of(Optional.of(data));
        }
    }

    @GetMapping("/getProductByCategery/{categery}")
    public ResponseEntity<DataResponse> allProductsbyCategery(@PathVariable("categery") String categery) {
        List<Product> products = new ArrayList<>();

        DataResponse response = new DataResponse();

        try {
            products = productServiceImpl.getProductbyCategery(categery);
            response.setStatus(HttpStatus.OK);
            response.setMessage("Products Fetched Successfully");
            response.setData(products);

            return ResponseEntity.of(Optional.of(response));

        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Products Fetched failed");
            response.setData(products);

            return ResponseEntity.of(Optional.of(response));

        }
    }

    @GetMapping("/getProductBySubCategery/{subcategery}")
    public ResponseEntity<DataResponse> allProductsbySubCategery(@PathVariable("subcategery") String subcategery) {
        List<Product> products = new ArrayList<>();

        DataResponse response = new DataResponse();

        try {
            products = productServiceImpl.getProdectbySubCategery(subcategery);
            response.setStatus(HttpStatus.OK);
            response.setMessage("Products Fetched Successfully");
            response.setData(products);

            return ResponseEntity.of(Optional.of(response));

        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Products Fetched Failed");
            response.setData(products);

            return ResponseEntity.of(Optional.of(response));
        }
    }

    @GetMapping("/getProductByCategoryOrSubCategory/{categery}/{subcategery}")
    public ResponseEntity<DataResponse> allProductsbyCategoryOrSubcategory(@PathVariable("categery") String category,
            @PathVariable("subcategery") String subcategery) {
        List<Product> products = new ArrayList<>();

        DataResponse response = new DataResponse();

        try {
            products = productServiceImpl.getProductsByCategoryOrSubcategory(category, subcategery);
            response.setStatus(HttpStatus.OK);
            response.setMessage("Products fetched Successfully");
            response.setData(products);

            return ResponseEntity.of(Optional.of(response));

        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Products fetched failed");
            response.setData(products);

            return ResponseEntity.of(Optional.of(response));
        }
    }

    @GetMapping("/getProductByCategoryAndSubCategory/{categery}/{subcategery}")
    public ResponseEntity<DataResponse> allProductsbyCategoryAndSubCategery(@PathVariable("categery") String category,
            @PathVariable("subcategery") String subcategery) {
        List<Product> products = new ArrayList<>();

        DataResponse response = new DataResponse();

        try {
            products = productServiceImpl.getProductsByCategoryAndSubcategory(category, subcategery);
            response.setStatus(HttpStatus.OK);
            response.setMessage("Products fetched Successfully");
            response.setData(products);

            return ResponseEntity.of(Optional.of(response));

        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Products fetched failed");
            response.setData(products);

            return ResponseEntity.of(Optional.of(response));
        }
    }

}
