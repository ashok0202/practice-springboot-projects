package com.tekworks.cloudinaryDemo.model;

public class ImageUploadResponse {
    private String publicId;
    private String url;
    private String format;

    // Constructors
    public ImageUploadResponse() {}

    public ImageUploadResponse(String publicId, String url, String format) {
        this.publicId = publicId;
        this.url = url;
        this.format = format;
    }

    // Getters and Setters
    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
