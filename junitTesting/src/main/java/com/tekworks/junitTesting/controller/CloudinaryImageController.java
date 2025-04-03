package com.tekworks.junitTesting.controller;

import com.tekworks.junitTesting.service.CloudinaryImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/student/upload")
public class CloudinaryImageController {

    @Autowired
    private CloudinaryImageService imageService;

    @PostMapping
    public ResponseEntity<Map> uploadImage(@RequestParam("Image")MultipartFile file){
        Map data=imageService.upload(file);
        return new ResponseEntity<>(data, HttpStatus.OK);


    }

}
