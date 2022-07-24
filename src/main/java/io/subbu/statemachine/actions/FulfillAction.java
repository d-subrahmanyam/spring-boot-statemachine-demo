package io.subbu.statemachine.actions;

import io.subbu.dtos.OrderDto;
import io.subbu.exceptions.ApplicationException;
import io.subbu.models.Order;
import io.subbu.services.OrderService;
import io.subbu.statemachine.constants.OrderEvents;
import io.subbu.statemachine.constants.OrderStates;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import static io.subbu.statemachine.constants.OrderConstants.ORDER_HEADER;

@Slf4j
public class FulfillAction implements Action<OrderStates, OrderEvents> {

    @Autowired
    private OrderService orderService;

    @Override
    public void execute(StateContext<OrderStates, OrderEvents> context) {
        OrderDto orderDto = (OrderDto) context.getStateMachine().getExtendedState().getVariables().get(ORDER_HEADER);
        orderDto = saveOrder(orderDto.getUuid());
        log.info("Fulfilled processing order  - {}", orderDto);
        context.getStateMachine().getExtendedState().getVariables().put(ORDER_HEADER, orderDto);
    }

    @SneakyThrows(ApplicationException.class)
    private OrderDto saveOrder(String uuid) {
        Order _order = orderService.get(uuid);
        if(_order.getTotalAmount().equals(_order.getAmountPaid())) {
            _order.setStatus(OrderStates.FULFILLED.name());
        }
        orderService.save(_order);
        return OrderDto.builder().build().createDtoFromEntity(_order);
    }
}