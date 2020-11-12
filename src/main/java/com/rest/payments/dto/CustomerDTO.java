package com.rest.payments.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rest.payments.model.Customer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDTO {

    private Long id;

    @NotNull(message = "Invalid 'First Name'")
    @Size(max = 254, message = "Invalid name length")
    private String firstName;

    @NotNull(message = "Invalid 'Last Name'")
    @Size(max = 254, message = "Invalid name length")
    private String lastName;

    @NotNull(message = "Invalid 'Address'")
    @Size(max = 254, message = "Invalid address length")
    private String address;

    @Max(value = 100000, message = "The zip code should have 5 digits")
    @Min(value = 10000, message = "The zip code should have 5 digits")
    private int zipCode;

    public CustomerDTO(final Customer customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.address = customer.getAddress();
        this.zipCode = customer.getZipCode();
    }

}
