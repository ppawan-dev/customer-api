package com.customer.api.customer.controller;

import com.customer.api.customer.modal.Customer;
import com.customer.api.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("customer")
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer addCustomer(@Valid @RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@Valid @PathVariable("id") String id) {
        Optional<Customer> customer = customerService.getCustomer(Long.parseLong(id));
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeCustomer(@Valid @PathVariable("id") String id) {
        if (!id.matches("\\d+")) {
            return ResponseEntity.badRequest().body("Invalid ID format. Please provide a numeric value.");
        }

        if(customerService.removeCustomer(Long.parseLong(id))) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/list")
    public ArrayList<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }
}