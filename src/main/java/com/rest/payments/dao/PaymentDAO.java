package com.rest.payments.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.rest.payments.model.Payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDAO extends JpaRepository<Payment, Long> {

    List<Payment> findAllByCustomerId(final Long id);

    Optional<Payment> findFirstByCustomerId(final Long id);

    @Transactional
    void deleteAllByCustomerId(final Long id);

}
