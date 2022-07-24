package io.subbu.components;

import io.subbu.dtos.OrderDto;
import io.subbu.statemachine.constants.OrderEvents;
import io.subbu.statemachine.constants.OrderStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import static io.subbu.statemachine.constants.OrderConstants.ORDER_HEADER;
import static io.subbu.statemachine.constants.OrderConstants.PAYMENT_HEADER;

@Order(23)
@Component
public class MakePayment implements CommandLineRunner {
    @Autowired
    private StateMachine<OrderStates, OrderEvents> stateMachine;

    @Override
    public void run(String... args) throws Exception {
        OrderDto orderDto = (OrderDto) stateMachine.getExtendedState().getVariables().get(ORDER_HEADER);
        String payment = String.valueOf(orderDto.getTotalAmount() - 1);
        Message<OrderEvents> makePaymentMessage =
                MessageBuilder.withPayload(OrderEvents.PAY)
                        .setHeader(PAYMENT_HEADER,payment)
                        .build();
        stateMachine.sendEvent(makePaymentMessage);
    }
}
