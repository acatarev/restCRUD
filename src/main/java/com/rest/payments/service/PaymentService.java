package com.rest.payments.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.String.format;

import com.rest.payments.dao.CustomerDAO;
import com.rest.payments.dao.PaymentDAO;
import com.rest.payments.dto.PaymentDTO;
import com.rest.payments.exceptions.ServiceException;
import com.rest.payments.model.Payment;

import static com.rest.payments.exceptions.ExceptionMessages.AN_ISSUE_OCCURRED;
import static com.rest.payments.exceptions.ExceptionMessages.NO_CUSTOMER_FOUND_MESSAGE;
import static com.rest.payments.exceptions.ExceptionMessages.NO_PAYMENT_FOUND_MESSAGE;

import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentDAO paymentDAO;

    private final CustomerDAO customerDAO;

    public List<PaymentDTO> getPayments() {
        final List<PaymentDTO> paymentDTOList = paymentDAO.findAll()
                .stream()
                .map(PaymentDTO::new)
                .collect(Collectors.toList());
        if (paymentDTOList.isEmpty()) {
            throw new ServiceException("There are no payments", NOT_FOUND);
        } else {
            return paymentDTOList;
        }
    }

    public PaymentDTO getPaymentByPaymentId(final Long id) {
        return new PaymentDTO(paymentDAO.findById(id)
                .orElseThrow(() -> new ServiceException(NO_PAYMENT_FOUND_MESSAGE + id, NOT_FOUND)));
    }

    public List<PaymentDTO> getPaymentsByCustomerId(final Long id) {
        if (!customerDAO.findById(id).isPresent()) {
            throw new ServiceException(NO_CUSTOMER_FOUND_MESSAGE + id, NOT_FOUND);
        }

        final List<PaymentDTO> paymentDTOList = paymentDAO.findAllByCustomerId(id)
                .stream()
                .map(PaymentDTO::new)
                .collect(Collectors.toList());

        if (!paymentDTOList.isEmpty()) {
            return paymentDTOList;
        } else {
            throw new ServiceException("There are no payments for customer id: " + id, NOT_FOUND);
        }
    }

    public PaymentDTO addPayment(final PaymentDTO paymentDTO) {
        if (!customerDAO.findById(paymentDTO.getCustomerId()).isPresent()) {
            throw new ServiceException(NO_CUSTOMER_FOUND_MESSAGE + paymentDTO.getCustomerId(), BAD_REQUEST);
        }
        try {
            return new PaymentDTO(paymentDAO.save(new Payment(paymentDTO)));
        } catch (Exception exception) {
            throw new ServiceException(format(AN_ISSUE_OCCURRED, "add payment"), BAD_REQUEST);
        }
    }

    public PaymentDTO updatePaymentById(final Long id, PaymentDTO paymentDTO) {
        paymentDTO.setId(id);
        Payment paymentToBeUpdated = paymentDAO.findById(paymentDTO.getId())
                .orElseThrow(() -> new ServiceException(NO_PAYMENT_FOUND_MESSAGE + id, NOT_FOUND));

        if (Objects.nonNull(paymentDTO.getCustomerId())) {
            paymentToBeUpdated.setCustomer(customerDAO.findById(paymentDTO.getCustomerId())
                    .orElseThrow(() -> new ServiceException(NO_CUSTOMER_FOUND_MESSAGE + paymentDTO.getCustomerId(), BAD_REQUEST)));
        }
        if (paymentDTO.getAmount() != null && paymentDTO.getAmount() > 0.0d) {
            paymentToBeUpdated.setAmount(paymentDTO.getAmount());
        }

        try {
            return new PaymentDTO(paymentDAO.save(paymentToBeUpdated));
        } catch (Exception exception) {
            throw new ServiceException(format(AN_ISSUE_OCCURRED, "update payment"), BAD_REQUEST);
        }
    }

    public String deletePaymentByPaymentId(final Long id) {
        try {
            paymentDAO.deleteById(id);
            return "The payment has been successfully removed";
        } catch (Exception exception) {
            throw new ServiceException(format(AN_ISSUE_OCCURRED, "remove payment"), BAD_REQUEST);
        }
    }

    public String deletePaymentsByCustomerId(final Long id) {
        if (!customerDAO.findById(id).isPresent()) {
            throw new ServiceException(NO_CUSTOMER_FOUND_MESSAGE + id, BAD_REQUEST);
        }
        if (paymentDAO.findAllByCustomerId(id).isEmpty()) {
            throw new ServiceException(format(AN_ISSUE_OCCURRED, "remove customer's payment(s)"), BAD_REQUEST);
        }

        paymentDAO.deleteAllByCustomerId(id);
        return "The payment(s) have been successfully removed";
    }
}
