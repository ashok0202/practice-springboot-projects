package com.tekworks.cloudinaryDemo.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tekworks.cloudinaryDemo.model.ImageUploadResponse;
import com.tekworks.cloudinaryDemo.model.VideoUploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    //    public Map upload(MultipartFile file){
//        try {
//            Map data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
//            return data;
//        } catch (IOException e) {
//            throw new RuntimeException("Image Uploading fail..!");
//        }
//
//    }
    public ImageUploadResponse uploadImage(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), Map.of(
                    "resource_type", "image",
                    "folder", "uploaded_images"
            ));

            return new ImageUploadResponse(
                    (String) uploadResult.get("public_id"),
                    (String) uploadResult.get("secure_url"),
                    (String) uploadResult.get("format")
            );

        } catch (IOException e) {
            throw new RuntimeException("Image upload failed!" + e.getMessage());
        }
    }

    public InputStream getImageStream(String publicId) {
        try {
            String imageUrl = cloudinary.url()
                    .secure(true)
                    .resourceType("image")
                    .generate(publicId); // No extra folder added

            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            return connection.getInputStream();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch image stream", e);
        }
    }


    public VideoUploadResponse uploadVideo(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap(
                        "resource_type", "video",
                        "folder", "uploaded_videos"
                ));

        String publicId = (String) uploadResult.get("public_id");
        String secureUrl = (String) uploadResult.get("secure_url");

        return new VideoUploadResponse(publicId, secureUrl);
    }

    public InputStream getVideoUrl(String publicId) {
        try {
            String videoUrl = cloudinary.url()
                    .resourceType("video")
                    .secure(true)
                    .generate(publicId);
            URL url = new URL(videoUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            return connection.getInputStream();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch getVideo stream " + e.getMessage());
        }
    }
}


