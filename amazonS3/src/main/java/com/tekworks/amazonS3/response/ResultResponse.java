package com.tekworks.amazonS3.response;

import org.springframework.beans.factory.annotation.Autowired;

public class ResultResponse {

    @Autowired
    private MetadataResponse metadataResponse;

    private Object data;


    public MetadataResponse getMetadataResponse() {
        return metadataResponse;
    }

    public void setMetadataResponse(MetadataResponse metadataResponse) {
        this.metadataResponse = metadataResponse;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
