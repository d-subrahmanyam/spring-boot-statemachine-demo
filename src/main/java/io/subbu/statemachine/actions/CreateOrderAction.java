package io.subbu.statemachine.actions;

import io.subbu.dtos.OrderDto;
import io.subbu.exceptions.ApplicationException;
import io.subbu.models.Order;
import io.subbu.services.OrderService;
import io.subbu.statemachine.constants.OrderEvents;
import io.subbu.statemachine.constants.OrderStates;
import io.subbu.statemachine.exceptions.OrderIdNotFoundException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import static io.subbu.statemachine.constants.OrderConstants.ORDER_HEADER;

@Slf4j
public class CreateOrderAction implements Action<OrderStates, OrderEvents> {

    @Autowired
    private OrderService orderService;

    @SneakyThrows
    @Override
    public void execute(StateContext<OrderStates, OrderEvents> context) {
        OrderDto orderDto = context.getMessage().getHeaders().get(ORDER_HEADER, OrderDto.class);
        log.info("Created order with [order details = {}]", orderDto);
        if(orderDto==null) throw new OrderIdNotFoundException();
        orderDto.setStatus(OrderStates.CREATE.name());
        saveOrder(orderDto.createEntity());
        context.getStateMachine().getExtendedState().getVariables().put(ORDER_HEADER, orderDto);
    }

    private void saveOrder(Order order) {
        orderService.save(order);
    }
}