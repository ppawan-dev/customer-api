package com.customer.api.customer.service;

import com.customer.api.customer.modal.Customer;
import com.customer.api.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomer(long id) {
        return customerRepository.findById(id);
    }

    public boolean removeCustomer(long id) {
        if(customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customersList = new ArrayList<>();
        customerRepository.findAll().iterator().forEachRemaining(customersList::add);
        return customersList;
    }
}