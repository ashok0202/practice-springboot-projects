package com.tekworks.spring_transaction.service;

import com.tekworks.spring_transaction.entity.Order;
import com.tekworks.spring_transaction.entity.Product;
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

    public OrderProcessingService(InventoryService inventoryService, OrderService orderService,AuditLogService auditLogService,PaymentValidatorService paymentValidatorService) {
        this.inventoryService = inventoryService;
        this.orderService = orderService;
        this.auditLogService=auditLogService;
        this.paymentValidatorService=paymentValidatorService;
    }


    //REQUIRED =join existing transaction or create new one if not available
    //REQUIRED_NEW = Always creates new transaction, suspending current transaction if available
    //MANDATORY = Always creates new transaction, throws exception if current transaction is suspended
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
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

        paymentValidatorService.validatePayment(order);
        // update product quantity
        // save order
        return saveOrder;
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
