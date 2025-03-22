package com.tekworks.spring_transaction.service;

import com.tekworks.spring_transaction.entity.Product;
import com.tekworks.spring_transaction.repository.InventoryRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
    public Product saveProduct(Product product) {
        return inventoryRepository.save(product);
    }
    public Product getProduct(int id) {
        return inventoryRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Product not found"));
    }


}
