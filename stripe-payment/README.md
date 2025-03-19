 # Stripe Payment Integration (Spring Boot)

This project integrates Stripe payment processing with a Spring Boot application.

## Features:
- Create a payment session with Stripe
- Handle success and cancellation URLs
- Validate product details before payment

## Dependencies:
- Spring Boot
- Stripe Java SDK (`com.stripe:stripe-java:24.3.0`)

## API Endpoint:
- **POST /checkout** → Creates a Stripe payment session

## Configuration:
Set your Stripe **Secret Key** in `application.properties`:

## posting data formate 

## Usage:
Send a **POST** request with the following JSON:
```json
{
    "amount": 10000,
    "quantity": 1,
    "currency": "INR",
    "name": "Mobile"
}

 Success Response
{
    "status": "SUCCESS",
    "message": "Payment Session created",
    "sessionId": "session_id_here",
    "sessionUrl": "https://checkout.stripe.com/pay/session_id_here"
}
