package io.subbu.statemachine.actions;

import io.subbu.dtos.OrderDto;
import io.subbu.statemachine.constants.OrderEvents;
import io.subbu.statemachine.constants.OrderStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import static io.subbu.statemachine.constants.OrderConstants.ORDER_HEADER;

@Slf4j
public class CancelAction implements Action<OrderStates, OrderEvents> {

    @Override
    public void execute(StateContext<OrderStates, OrderEvents> context) {
        OrderDto orderDto = (OrderDto) context.getStateMachine().getExtendedState().getVariables().get(ORDER_HEADER);
        log.info("Cancelled processing order - {}", orderDto);
        orderDto.setStatus(OrderStates.CANCELLED.name());
        context.getStateMachine().getExtendedState().getVariables().put(ORDER_HEADER, orderDto);
    }
}