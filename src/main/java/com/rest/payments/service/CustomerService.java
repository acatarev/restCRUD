package com.rest.payments.service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

import com.rest.payments.dao.CustomerDAO;
import com.rest.payments.dao.PaymentDAO;
import com.rest.payments.dto.CustomerDTO;
import com.rest.payments.exceptions.ServiceException;
import com.rest.payments.model.Customer;

import static com.rest.payments.exceptions.ExceptionMessages.AN_ISSUE_OCCURRED;
import static com.rest.payments.exceptions.ExceptionMessages.NO_CUSTOMER_FOUND_MESSAGE;

import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;

    private final PaymentDAO paymentDAO;

    public List<CustomerDTO> getCustomers() {
        List<CustomerDTO> customerDTOList = customerDAO.findAll()
                .stream()
                .map(CustomerDTO::new)
                .collect(Collectors.toList());
        if (customerDTOList.isEmpty()) {
            throw new ServiceException("There are no customers", NOT_FOUND);
        } else {
            return customerDTOList;
        }
    }

    public CustomerDTO getCustomerById(final Long id) {
        return new CustomerDTO(customerDAO.findById(id)
                .orElseThrow(() -> new ServiceException(NO_CUSTOMER_FOUND_MESSAGE + id, NOT_FOUND)));
    }

    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        try {
            return new CustomerDTO(customerDAO.save(new Customer(customerDTO)));
        } catch (Exception exception) {
            throw new ServiceException(format(AN_ISSUE_OCCURRED, "add customer"), BAD_REQUEST);
        }
    }

    public CustomerDTO updateCustomerById(final Long id, final CustomerDTO customerDTO) {
        customerDTO.setId(id);
        Customer toBeUpdated = customerDAO.findById(customerDTO.getId())
                .orElseThrow(() -> new ServiceException(NO_CUSTOMER_FOUND_MESSAGE + id, NOT_FOUND));

        if (customerDTO.getFirstName() != null) {
            toBeUpdated.setFirstName(customerDTO.getFirstName());
        }
        if (customerDTO.getLastName() != null) {
            toBeUpdated.setLastName(customerDTO.getLastName());
        }
        if (customerDTO.getAddress() != null) {
            toBeUpdated.setAddress(customerDTO.getAddress());
        }
        if (customerDTO.getZipCode() > 9999 && customerDTO.getZipCode() < 100000) {
            toBeUpdated.setZipCode(customerDTO.getZipCode());
        }

        try {
            return new CustomerDTO(customerDAO.save(toBeUpdated));
        } catch (Exception exception) {
            throw new ServiceException(format(AN_ISSUE_OCCURRED, "update customer"), BAD_REQUEST);
        }
    }

    public String deleteCustomerById(final Long id) {
        if (!customerDAO.findById(id).isPresent()) {
            throw new ServiceException(format(AN_ISSUE_OCCURRED, "remove customer"), BAD_REQUEST);
        }
        paymentDAO.findFirstByCustomerId(id).ifPresent(payment -> {
            throw new ServiceException("There are payment(s) for customer with id: " + id, BAD_REQUEST);
        });

        customerDAO.deleteById(id);
        return "The user has been successfully removed";
    }
}
