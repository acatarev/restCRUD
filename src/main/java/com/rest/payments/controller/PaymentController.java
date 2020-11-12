package com.rest.payments.controller;

import javax.validation.Valid;

import com.rest.payments.dto.PaymentDTO;
import com.rest.payments.service.PaymentService;

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
@RequestMapping("/rest/payments")
public class PaymentController implements Controller {

    private final PaymentService paymentService;

    @GetMapping("/")
    public ResponseEntity<Object> getAllPayments() {
        return new ResponseEntity<>(paymentService.getPayments(), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPaymentById(@PathVariable final Long id) {
        return new ResponseEntity<>(paymentService.getPaymentByPaymentId(id), OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Object> getPaymentsByCustomerId(@PathVariable final Long id) {
        return new ResponseEntity<>(paymentService.getPaymentsByCustomerId(id), OK);
    }

    @PostMapping("/")
    public ResponseEntity<Object> addPayment(@RequestBody @Valid final PaymentDTO paymentDTO) {
        return new ResponseEntity<>(paymentService.addPayment(paymentDTO), CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePayment(@PathVariable final Long id, @RequestBody final PaymentDTO paymentDTO) {
        return new ResponseEntity<>(paymentService.updatePaymentById(id, paymentDTO), OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePayment(@PathVariable final Long id) {
        return new ResponseEntity<>(paymentService.deletePaymentByPaymentId(id), OK);
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<Object> deletePaymentsByCustomerId(@PathVariable final Long id) {
        return new ResponseEntity<>(paymentService.deletePaymentsByCustomerId(id), OK);
    }
}
