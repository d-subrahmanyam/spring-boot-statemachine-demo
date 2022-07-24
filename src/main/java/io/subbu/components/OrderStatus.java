package io.subbu.components;

import io.subbu.dtos.OrderDto;
import io.subbu.statemachine.constants.OrderEvents;
import io.subbu.statemachine.constants.OrderStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import static io.subbu.statemachine.constants.OrderConstants.ORDER_HEADER;

@Order(25)
@Slf4j
@Component
public class OrderStatus implements CommandLineRunner {
    @Autowired
    private StateMachine<OrderStates, OrderEvents> stateMachine;

    @Override
    public void run(String... args) throws Exception {
        OrderDto orderDto = (OrderDto) stateMachine.getExtendedState().getVariables().get(ORDER_HEADER);
        log.info("Order [status - {}] for [order - {}]", orderDto.getStatus(), orderDto);
    }
}
