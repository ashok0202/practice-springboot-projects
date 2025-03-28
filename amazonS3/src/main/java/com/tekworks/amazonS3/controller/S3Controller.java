package com.tekworks.amazonS3.controller;

import com.tekworks.amazonS3.response.ResultResponse;
import com.tekworks.amazonS3.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/s3")
public class S3Controller {
    @Autowired
    private S3Service s3Service;

    @PostMapping("/upload")
    public ResultResponse uploadFile(@RequestParam("file") MultipartFile file) {
        return s3Service.uploadFile(file);
    }

    @DeleteMapping("/delete")
    public ResultResponse deleteFile(@RequestParam("fileName") String fileName) {
        return s3Service.deleteFile(fileName);

    }
    @GetMapping("/download/{file}")
    public ResponseEntity<Object> downloadFile(@PathVariable("file") String file) {

        try {
            byte[] byteArray=s3Service.downloadFile(file);
            return   ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(byteArray);
        }catch (Exception e){
            return new ResponseEntity<>("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
