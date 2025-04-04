package com.tekworks.cloudinaryDemo.service;

import com.amazonaws.services.s3.AmazonS3;
import com.tekworks.cloudinaryDemo.model.VideoUploadResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class S3Service {

    @Value("${application.bucket.name}")
    private String bucketName;

    private final AmazonS3 s3Client;

    public S3Service(AmazonS3 s3Client) {
        this.s3Client=s3Client;
    }


    public VideoUploadResponse uploadVideo(MultipartFile file) {
        try {

            File fileToUpload = File.createTempFile(UUID.randomUUID().toString(), file.getOriginalFilename());
            file.transferTo(fileToUpload);

            // Generate unique file name (publicId)
            String publicId ="testVideos/" +file.getOriginalFilename();
            s3Client.putObject(bucketName, publicId, fileToUpload);

            fileToUpload.delete();

            String secureUrl = "https://" + bucketName + ".s3.amazonaws.com/" + publicId;

            return new VideoUploadResponse(publicId, secureUrl);

        } catch (Exception e) {
            throw new RuntimeException("Video upload failed: " + e.getMessage());
        }
    }


    public String downloadVideo(String fileName) {
        try {
            String publicKey = "testVideos/" + fileName;
            return s3Client.getUrl(bucketName, publicKey).toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to download file from S3: " + e.getMessage());
        }
    }
}
