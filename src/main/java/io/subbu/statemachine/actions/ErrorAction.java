package io.subbu.statemachine.actions;

import io.subbu.statemachine.constants.OrderEvents;
import io.subbu.statemachine.constants.OrderStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

@Slf4j
public class ErrorAction implements Action<OrderStates, OrderEvents> {

    @Override
    public void execute(StateContext<OrderStates, OrderEvents> context) {
        String orderId = context.getMessage().getHeaders().get("ORDER_ID_HEADER", String.class);
        log.info("Error occurred while processing order id - " + orderId);
    }
}