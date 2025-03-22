package com.tekworks.spring_transaction.service;

import com.tekworks.spring_transaction.entity.Order;
import com.tekworks.spring_transaction.entity.Product;

public class OrderProcessingService {

    private final InventoryService inventoryService;
    private final OrderService orderService;

    public OrderProcessingService(InventoryService inventoryService, OrderService orderService) {
        this.inventoryService = inventoryService;
        this.orderService = orderService;
    }


    public Order placeOrder(Order order) {
        Product product = inventoryService.getProduct(order.getProductId());
        order.setTotalPrice(product.getPrice() * order.getQuantity());
        return orderService.createOrder(order);
    }
}
