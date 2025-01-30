package com.customer.api.customer.controller;

import com.customer.api.customer.modal.Customer;
import com.customer.api.customer.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CustomerService customerService;

    @Test
    public void testAddCustomer() throws Exception {
        Customer mockCustomer = new Customer();
        mockCustomer.setId(1);
        mockCustomer.setName("Spider man");
        mockCustomer.setAge(22);
        mockCustomer.setDob(LocalDate.of(1990, 11, 12));

        when(customerService.addCustomer(mockCustomer)).thenReturn(mockCustomer);

        mockMvc.perform(post("/customer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(mockCustomer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testAddCustomer_InvalidAge_InvalidDate() throws Exception {
        Customer mockCustomer = new Customer();
        mockCustomer.setId(1);
        mockCustomer.setName("Spider man");
        mockCustomer.setAge(12);
        mockCustomer.setDob(LocalDate.of(2099, 11, 12));

        when(customerService.addCustomer(mockCustomer)).thenReturn(mockCustomer);

        mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockCustomer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.age").value("Age must be at least 18"))
                .andExpect(jsonPath("$.dob").value("Date must be in the past or present"));
    }

    @Test
    public void testGetCustomer() {
        Customer mockCustomer = new Customer();
        mockCustomer.setId(1);
        mockCustomer.setName("Spider man");
        mockCustomer.setAge(22);
        mockCustomer.setDob(LocalDate.of(1990, 11, 12));

        when(customerService.getCustomer(mockCustomer.getId())).thenReturn(Optional.of(mockCustomer));

        try {
            mockMvc.perform(get("/customer/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetCustomer_NegativeID() {
        Customer mockCustomer = new Customer();
        mockCustomer.setId(-1);
        mockCustomer.setName("Spider man");
        mockCustomer.setAge(22);
        mockCustomer.setDob(LocalDate.of(1990, 11, 12));

        when(customerService.getCustomer(mockCustomer.getId())).thenReturn(Optional.of(mockCustomer));

        try {
            mockMvc.perform(get("/customer/1"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testRemoveCustomer() {
        Customer mockCustomer = new Customer();
        mockCustomer.setId(1);
        mockCustomer.setName("Spider man");
        mockCustomer.setAge(22);
        mockCustomer.setDob(LocalDate.of(1990, 11, 12));

        when(customerService.removeCustomer(mockCustomer.getId())).thenReturn(true);

        try {
            mockMvc.perform(delete("/customer/1"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetCustomers() {
        Customer mockCustomerSpiderMan = new Customer();
        mockCustomerSpiderMan.setId(1);
        mockCustomerSpiderMan.setName("Spider man");
        mockCustomerSpiderMan.setAge(22);
        mockCustomerSpiderMan.setDob(LocalDate.of(1990, 11, 12));

        Customer mockCustomerSuperman = new Customer();
        mockCustomerSuperman.setId(2);
        mockCustomerSuperman.setName("Super man");
        mockCustomerSuperman.setAge(20);
        mockCustomerSuperman.setDob(LocalDate.of(1992, 11, 12));

        ArrayList<Customer> mockCustomers = new ArrayList<>();
        mockCustomers.add(mockCustomerSpiderMan);
        mockCustomers.add(mockCustomerSuperman);

        when(customerService.getAllCustomers()).thenReturn(mockCustomers);

        try {
            mockMvc.perform(get("/customer/list"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].name").value("Spider man"))
                    .andExpect(jsonPath("$[1].name").value("Super man"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}