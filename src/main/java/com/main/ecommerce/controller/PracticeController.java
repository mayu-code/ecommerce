package com.main.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.main.ecommerce.services.impl.ImageUploader;

@RestController
public class PracticeController {

    @Autowired
    private ImageUploader imageUploader;
    
    @PostMapping("/images")
    public String imageAdder(@RequestParam("image")MultipartFile file){
        String url = imageUploader.iamgeUploader(file);
        return url;
    }

}
