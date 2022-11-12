package com.example.demostatemachine.service;

import com.example.demostatemachine.domain.Payment;
import com.example.demostatemachine.domain.PaymentState;
import com.example.demostatemachine.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class PaymentStateChangeInterceptor extends StateMachineInterceptorAdapter {

    private final PaymentRepository paymentRepository;

    @Override
    public void preStateChange(State state, Message message,
                               Transition transition, StateMachine stateMachine) {
        Optional.ofNullable(message).ifPresent(
                msg -> {
                    Optional.ofNullable(Long.class.cast(msg.getHeaders().getOrDefault(PaymentServiceImpl.PAYMENT_ID_HEADER, -1L)))
                            .ifPresent(paymentId -> {
                                Payment payment = paymentRepository.getReferenceById(paymentId);
                                payment.setState((PaymentState) state.getId());
                                paymentRepository.save(payment);
                            });
                }
        );
    }
}
