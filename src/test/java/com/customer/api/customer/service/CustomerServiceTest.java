package com.customer.api.customer.service;

import com.customer.api.customer.modal.Customer;
import com.customer.api.customer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void testAddCustomer() {
        Customer mockCustomer = new Customer();
        mockCustomer.setAge(25);
        mockCustomer.setName("Jack Sparrow");
        mockCustomer.setDob(LocalDate.of(1990, 2, 25));

        when(customerRepository.save(mockCustomer)).thenReturn(mockCustomer);

        Customer result = customerService.addCustomer(mockCustomer);

        assertEquals(mockCustomer.getName(), result.getName());
        assertTrue(null != String.valueOf(result.getId()), "Customer ID is null");
    }

    @Test
    public void testGetCustomer() {
        Customer mockCustomer = new Customer();
        mockCustomer.setAge(20);
        mockCustomer.setName("Peter Parker");
        mockCustomer.setDob(LocalDate.of(1995, 5, 21));

        when(customerRepository.save(mockCustomer)).thenReturn(mockCustomer);
        when(customerRepository.findById(mockCustomer.getId())).thenReturn(Optional.of(mockCustomer));

        Customer savedCustomer = customerRepository.save(mockCustomer);
        Optional<Customer> result = customerService.getCustomer(savedCustomer.getId());

        assertEquals(savedCustomer.getId(), result.get().getId());
    }

    @Test
    public void testRemoveExistingCustomer() {
        Customer mockCustomer = new Customer();
        mockCustomer.setAge(55);
        mockCustomer.setName("Green Goblin");
        mockCustomer.setDob(LocalDate.of(1967, 2, 15));

        when(customerRepository.save(mockCustomer)).thenReturn(mockCustomer);
        when(customerRepository.existsById(mockCustomer.getId())).thenReturn(true);

        Customer savedCustomer = customerRepository.save(mockCustomer);
        boolean isDeleted = customerService.removeCustomer(savedCustomer.getId());

        assertTrue(isDeleted);
        verify(customerRepository, times(1)).existsById(savedCustomer.getId());
        verify(customerRepository, times(1)).deleteById(savedCustomer.getId());
    }

    @Test
    public void testRemoveNonExistingCustomer() {

        when(customerRepository.existsById(100L)).thenReturn(false);

        boolean isDeleted = customerService.removeCustomer(100);

        assertFalse(isDeleted);
        verify(customerRepository, times(1)).existsById(100L);
        verify(customerRepository, times(0)).deleteById(100L);

    }

    @Test
    public void testGetAllCustomers() {
        Customer mockCustomerBatman = new Customer();
        mockCustomerBatman.setAge(24);
        mockCustomerBatman.setName("Bruce Wayne");
        mockCustomerBatman.setDob(LocalDate.of(1988, 7, 18));

        Customer mockCustomerSpiderMan = new Customer();
        mockCustomerSpiderMan.setAge(23);
        mockCustomerSpiderMan.setName("Spider man");
        mockCustomerSpiderMan.setDob(LocalDate.of(1990, 11, 20));

        ArrayList<Customer> customersList = new ArrayList<>();
        customersList.add(mockCustomerBatman);
        customersList.add(mockCustomerSpiderMan);

        when(customerRepository.save(mockCustomerBatman)).thenReturn(mockCustomerBatman);
        when(customerRepository.save(mockCustomerSpiderMan)).thenReturn(mockCustomerSpiderMan);
        when(customerRepository.findAll()).thenReturn(customersList);

        customerService.addCustomer(mockCustomerBatman);
        customerService.addCustomer(mockCustomerSpiderMan);

        ArrayList<Customer> result = customerService.getAllCustomers();
        assertEquals(2, result.size());
    }
}
