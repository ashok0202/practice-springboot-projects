package com.tekworks.amazonS3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.tekworks.amazonS3.response.MetadataResponse;
import com.tekworks.amazonS3.response.ResultResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class S3Service {

    @Value("${application.bucket.name}")
    private String bucketName;

    private final AmazonS3 s3Client;

    public S3Service(AmazonS3 s3Client) {
        this.s3Client=s3Client;
    }
    public ResultResponse uploadFile(MultipartFile file) {

        MetadataResponse response =new MetadataResponse();
        ResultResponse resultResponse =new ResultResponse();
        try {

            File fileSave=convertMultiPartToFile(file);
            String fileName=file.getOriginalFilename();
            s3Client.putObject(bucketName,fileName,fileSave);
            response.setCode("200");
            response.setMessage("File uploaded successfully");
            response.setNoOfRecords("1");
            resultResponse.setMetadataResponse(response);
            resultResponse.setData(fileName);
            return resultResponse;
        }
        catch (Exception e) {
            response.setCode("500");
            response.setMessage(e.getMessage());
            response.setNoOfRecords("0");
            resultResponse.setMetadataResponse(response);
            resultResponse.setData(null);
            return resultResponse;

        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {

        File convFile = new File(file.getOriginalFilename());

        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public byte[] downloadFile(String fileName) {
        MetadataResponse metadataResponse = new MetadataResponse();
        ResultResponse resultResponse = new ResultResponse();

        try {

            S3Object object = s3Client.getObject(bucketName, fileName);

            byte[] byteArray=IOUtils.toByteArray(object.getObjectContent());


            return byteArray;
        } catch (Exception e) {
            metadataResponse.setCode("500");
            metadataResponse.setMessage("Error: " + e.getMessage());
            metadataResponse.setNoOfRecords("0");

            resultResponse.setMetadataResponse(metadataResponse);
            resultResponse.setData(null);
            return null;
        }
    }

    public ResultResponse deleteFile(String fileName) {

        MetadataResponse response =new MetadataResponse();
        ResultResponse resultResponse =new ResultResponse();
        try {


            s3Client.deleteObject(bucketName,fileName);
            response.setCode("200");
            response.setMessage("File uploaded successfully");
            response.setNoOfRecords("1");
            resultResponse.setMetadataResponse(response);
            resultResponse.setData(fileName);
            return resultResponse;
        }
        catch (Exception e) {
            response.setCode("500");
            response.setMessage(e.getMessage());
            response.setNoOfRecords("0");
            resultResponse.setMetadataResponse(response);
            resultResponse.setData(null);
            return resultResponse;

        }
    }
}
