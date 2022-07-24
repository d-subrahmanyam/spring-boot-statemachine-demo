package io.subbu.components;

import io.subbu.dtos.OrderDto;
import io.subbu.mock.generators.MockOrderDtoGenerator;
import io.subbu.statemachine.constants.OrderEvents;
import io.subbu.statemachine.constants.OrderStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import static io.subbu.statemachine.constants.OrderConstants.*;

@Order(22)
@Component
public class CreateOrder implements CommandLineRunner {
    @Autowired
    private StateMachine<OrderStates, OrderEvents> stateMachine;

    @Autowired
    private MockOrderDtoGenerator mockOrderGenerator;

    @Override
    public void run(String... args) throws Exception {
        OrderDto _order = mockOrderGenerator.generate();
        Message<OrderEvents> placeOrderMessage =
                MessageBuilder.withPayload(OrderEvents.CREATE_ORDER)
                        .setHeader(ORDER_HEADER, _order)
                        .build();
        stateMachine.sendEvent(placeOrderMessage);
    }

}
