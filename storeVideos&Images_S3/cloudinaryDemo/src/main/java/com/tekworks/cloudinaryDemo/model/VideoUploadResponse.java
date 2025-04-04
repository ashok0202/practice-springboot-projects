package com.tekworks.cloudinaryDemo.model;

public class VideoUploadResponse {
    private String publicId;
    private String secureUrl;


    public VideoUploadResponse() {
    }

    public VideoUploadResponse(String publicId, String secureUrl) {
        this.publicId = publicId;
        this.secureUrl = secureUrl;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getSecureUrl() {
        return secureUrl;
    }

    public void setSecureUrl(String secureUrl) {
        this.secureUrl = secureUrl;
    }
}