package com.rest.payments.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rest.payments.dto.PaymentDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "payments")
public class Payment {

    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "FK_Customer"))
    private Customer customer;

    @Column(name = "amount")
    private Double amount;

    public Payment(final PaymentDTO paymentDTO) {
        this.id = paymentDTO.getId();
        this.customer.setId(paymentDTO.getCustomerId());
        this.amount = paymentDTO.getAmount();
    }

}
