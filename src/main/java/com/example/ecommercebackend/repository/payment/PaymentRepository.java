package com.example.ecommercebackend.repository.payment;

import com.example.ecommercebackend.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByConversationId(String conversationId);

}
