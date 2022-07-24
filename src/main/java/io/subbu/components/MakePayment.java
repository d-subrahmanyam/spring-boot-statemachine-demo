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
        // uncomment the below line to make a payment of orderAmount -1 to trigger the ErrorAction
        //String payment = String.valueOf(orderDto.getTotalAmount() - 1);
        // comment this line in-case you are testing the error scenario
        // making the exact payment as the orderAmount to move to the FulfillmentAction
        String payment = String.valueOf(orderDto.getTotalAmount());
        Message<OrderEvents> makePaymentMessage =
                MessageBuilder.withPayload(OrderEvents.PAY)
                        .setHeader(PAYMENT_HEADER,payment)
                        .build();
        stateMachine.sendEvent(makePaymentMessage);
    }
}
