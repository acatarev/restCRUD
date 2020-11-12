package com.rest.payments.controller;

import javax.validation.Valid;

import com.rest.payments.dto.CustomerDTO;
import com.rest.payments.service.CustomerService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/customers")
public class CustomerController implements Controller {

    private final CustomerService customerService;

    @GetMapping("/")
    public ResponseEntity<Object> getAllCustomers() {
        return new ResponseEntity<>(customerService.getCustomers(), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable final Long id) {
        return new ResponseEntity<>(customerService.getCustomerById(id), OK);
    }

    @PostMapping("/")
    public ResponseEntity<Object> addCustomer(@RequestBody @Valid final CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.addCustomer(customerDTO), CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable final Long id, @RequestBody final CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.updateCustomerById(id, customerDTO), OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable final Long id) {
        return new ResponseEntity<>(customerService.deleteCustomerById(id), OK);
    }

}
