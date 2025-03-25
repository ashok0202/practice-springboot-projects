package com.tekworks.spring_transaction.service;

import com.tekworks.spring_transaction.entity.Order;
import com.tekworks.spring_transaction.entity.Product;
import com.tekworks.spring_transaction.handler.ProductRecommendationHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderProcessingService {

    private final InventoryService inventoryService;
    private final OrderService orderService;
    private final AuditLogService auditLogService;
    private final PaymentValidatorService paymentValidatorService;
    private final NotificationService notificationService;

    private final ProductRecommendationHandler productRecommendationHandler;

    public OrderProcessingService(
            InventoryService inventoryService,
            OrderService orderService,
            AuditLogService auditLogService,
            PaymentValidatorService paymentValidatorService,
            NotificationService notificationService,
            ProductRecommendationHandler productRecommendationHandler) {
        this.inventoryService = inventoryService;
        this.orderService = orderService;
        this.auditLogService=auditLogService;
        this.paymentValidatorService=paymentValidatorService;
        this.notificationService=notificationService;
        this.productRecommendationHandler=productRecommendationHandler;
    }


    //REQUIRED =join existing transaction or create new one if not available
    //REQUIRED_NEW = Always creates new transaction, suspending current transaction if available
    //MANDATORY = Always creates new transaction, throws exception if current transaction is suspended
    //NEVER = Ensures the method will run without a transaction, throwing an exception if a transaction is active
    //NOT_SUPPORTED = Ensures the method will run without a transaction, suspending a current transaction if available
    //SUPPORTS = Ensures the method will run with a transaction, suspending a current transaction if available
    //NESTED = Ensures the method will run with a transaction, creating a new nested transaction if a current transaction is active
    @Transactional(propagation = Propagation.REQUIRED)
    public Order placeOrder(Order order) {

        //get product
        Product product = inventoryService.getProduct(order.getProductId());

        //validation stack available();
        validateStockAvailability(order, product);

       Order saveOrder=null;
        try{
            saveOrder=orderService.createOrder(order);
            //update total price in order entity
            updateProductStock(order, product);
            auditLogService.logAuditDetails(order,"Success");
        }catch (Exception e){
            auditLogService.logAuditDetails(order,"Failed");
            throw e;
        }

        notificationService.sendOrderConfirmation(order);

       paymentValidatorService.validatePayment(order);
        //paymentValidatorService.validatePaymentNested(order);
        // update product quantity
        // save order
        //productRecommendationHandler.getRecommendations();
        getCustomerDetails();
        return saveOrder;
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public void getCustomerDetails() {
        System.out.println("Customer details fetched !!!!!");
    }
    private void validateStockAvailability(Order order, Product product) {
        if(order.getQuantity() > product.getStockQuantity()) {
            throw new RuntimeException("Product not available");
        }
    }

    private void updateProductStock(Order order, Product product) {
        order.setTotalPrice(product.getPrice() * order.getQuantity());
        product.setStockQuantity(product.getStockQuantity() - order.getQuantity());
        inventoryService.saveProduct(product);
    }
}
