package com.rest.payments.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.rest.payments.model.Payment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentDTO {

    private Long id;

    @Positive
    private Long customerId;

    @PositiveOrZero
    private Double amount;

    public PaymentDTO(final Payment payment) {
        this.id = payment.getId();
        this.customerId = payment.getCustomer().getId();
        this.amount = payment.getAmount();
    }
}
