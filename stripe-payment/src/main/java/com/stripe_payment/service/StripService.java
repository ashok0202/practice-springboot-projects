package com.stripe_payment.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe_payment.model.ErrorResponse;
import com.stripe_payment.model.ProductRequest;
import com.stripe_payment.model.StripResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripService {

    @Value("${stripe.secretKey}")
    private String securityKey;

    public Object createSession(ProductRequest productRequest) {
        try {
            Stripe.apiKey = securityKey;

            // Validate amount
            if (productRequest.getAmount() == null || productRequest.getAmount() <= 0) {
                return new ErrorResponse(400, "Amount must be greater than 0", "Invalid payment amount");
            }

            // Create ProductData
            SessionCreateParams.LineItem.PriceData.ProductData productData =
                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                            .setName(productRequest.getName())
                            .build();

            // Create PriceData
            SessionCreateParams.LineItem.PriceData priceData =
                    SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency(productRequest.getCurrency() == null ? "usd" : productRequest.getCurrency().toLowerCase())
                            .setProductData(productData)
                            .setUnitAmount(productRequest.getAmount() * 100) // Convert to cents
                            .build();

            // Create LineItem
            SessionCreateParams.LineItem lineItem =
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(productRequest.getQuantity())
                            .setPriceData(priceData)
                            .build();

            // Create Stripe Session
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:8080/success")
                    .setCancelUrl("http://localhost:8080/cancel")
                    .addLineItem(lineItem)
                    .build();

            Session session = Session.create(params);

            // Return success response
            StripResponse stripResponse = new StripResponse();
            stripResponse.setStatus("SUCCESS");
            stripResponse.setMessage("Payment Session created");
            stripResponse.setSessionId(session.getId());
            stripResponse.setSessionUrl(session.getUrl());

            return stripResponse;

        } catch (StripeException e) {
            return new ErrorResponse(500, "Stripe API Error", e.getMessage());
        } catch (Exception e) {
            return new ErrorResponse(500, "Unexpected Error", e.getMessage());
        }
    }
}
