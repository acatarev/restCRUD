package com.rest.payments.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rest.payments.dto.CustomerDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "customers")
public class Customer {

    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "zip_code")
    private Integer zipCode;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)//orphanRemoval = true)
    private Set<Payment> payments;

    public Customer(final CustomerDTO customerDTO) {
        this.id = customerDTO.getId();
        this.firstName = customerDTO.getFirstName();
        this.lastName = customerDTO.getLastName();
        this.address = customerDTO.getAddress();
        this.zipCode = customerDTO.getZipCode();
    }
}

