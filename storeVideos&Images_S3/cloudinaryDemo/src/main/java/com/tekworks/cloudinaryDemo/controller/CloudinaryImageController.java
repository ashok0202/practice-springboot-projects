package com.tekworks.cloudinaryDemo.controller;


import com.tekworks.cloudinaryDemo.model.ImageUploadResponse;
import com.tekworks.cloudinaryDemo.model.VideoUploadResponse;
import com.tekworks.cloudinaryDemo.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@RestController
@RequestMapping("/student/upload")
public class CloudinaryImageController {

    @Autowired
    private CloudinaryService imageService;



    @PostMapping("/image")
    public ResponseEntity<ImageUploadResponse> uploadImage(@RequestParam("Image")MultipartFile file){
        ImageUploadResponse data=imageService.uploadImage(file);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }



    @PostMapping("/video")
    public ResponseEntity<VideoUploadResponse> uploadVideo(@RequestParam("file") MultipartFile file) {
        try {
            VideoUploadResponse response = imageService.uploadVideo(file);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new VideoUploadResponse("ERROR", e.getMessage()));
        }
    }

//    @GetMapping("/getUrl")
//    public ResponseEntity<String> getVideoUrl(@RequestParam("public_id") String publicId) {
//        String videoUrl = imageService.getVideoUrl(publicId);
//        return ResponseEntity.ok(videoUrl);
//    }
@GetMapping("/view")
public ResponseEntity<Object> viewImage(@RequestParam("public_id") String publicId) {
    try{
        InputStream imageStream = imageService.getImageStream(publicId);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // or PNG depending on your image
                .body(new InputStreamResource(imageStream));

    } catch (Exception e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }
}

    @GetMapping("/getVideo")
    public ResponseEntity<InputStreamResource> getVideoStream(@RequestParam("public_id") String publicId) {
        try {
            InputStream videoUrl = imageService.getVideoUrl(publicId);  // get Cloudinary video URL
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + publicId + ".mp4\"")
                    .contentType(MediaType.parseMediaType("video/mp4"))
                    .body(new InputStreamResource(videoUrl));

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }


}