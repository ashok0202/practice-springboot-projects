package com.tekworks.hibernateN._Problem.controller;

import com.tekworks.hibernateN._Problem.entity.Customer;
import com.tekworks.hibernateN._Problem.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return service.saveCustomer(customer);
    }


    @GetMapping
    public List<Customer> getCustomers() {
        return service.getAllCustomers();
    }
}
