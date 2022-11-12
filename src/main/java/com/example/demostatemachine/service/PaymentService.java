package com.example.demostatemachine.service;

import com.example.demostatemachine.domain.Payment;
import com.example.demostatemachine.domain.PaymentEvent;
import com.example.demostatemachine.domain.PaymentState;
import org.springframework.statemachine.StateMachine;

public interface PaymentService {

    Payment newPayment(Payment payment);

    StateMachine<PaymentState, PaymentEvent> preAuth(Long paymentId);
    StateMachine<PaymentState, PaymentEvent> authorizePayment(Long paymentId);
    StateMachine<PaymentState, PaymentEvent> declineAuth (Long paymentId);

}
