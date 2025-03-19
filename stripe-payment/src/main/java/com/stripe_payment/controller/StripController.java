package com.stripe_payment.controller;

import com.stripe_payment.model.ErrorResponse;
import com.stripe_payment.model.ProductRequest;
import com.stripe_payment.model.StripResponse;
import com.stripe_payment.service.StripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class StripController {

    private StripService stripService;

    public StripController(StripService stripService) {
        this.stripService = stripService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<Object> stripResponse(@RequestBody ProductRequest productRequest) {
        Object response = stripService.createSession(productRequest);
        if (response instanceof StripResponse) {
            return ResponseEntity.ok(response); // Success case
        } else if (response instanceof ErrorResponse) {
            return ResponseEntity.status(((ErrorResponse) response).getStatusCode()).body(response);
        }// Error case

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, "Unknown error", "Something went wrong"));
    }
}





