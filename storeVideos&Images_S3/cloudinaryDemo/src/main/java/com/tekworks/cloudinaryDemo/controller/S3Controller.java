package com.tekworks.cloudinaryDemo.controller;

import com.tekworks.cloudinaryDemo.model.VideoUploadResponse;
import com.tekworks.cloudinaryDemo.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/s3")
@CrossOrigin("*")
public class S3Controller {
    @Autowired
    private S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<VideoUploadResponse> uploadVideo(@RequestParam("file") MultipartFile file) {
        if(file.getSize()>250000000)
            return ResponseEntity.status(500).body(
                    new VideoUploadResponse("ERROR", "File size should be less than 250MB")
            );
        VideoUploadResponse response = s3Service.uploadVideo(file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getVideo/{fileName}")
    public String getVideoUrl(@PathVariable String fileName) {
        return s3Service.downloadVideo(fileName);
    }




}
