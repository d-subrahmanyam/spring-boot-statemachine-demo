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

import static io.subbu.statemachine.constants.OrderConstants.*;

@Slf4j
public class PaymentAction implements Action<OrderStates, OrderEvents> {

    @Autowired
    private OrderService orderService;

    @SneakyThrows
    @Override
    public void execute(StateContext<OrderStates, OrderEvents> context) {
        OrderDto orderDto = (OrderDto) context.getStateMachine().getExtendedState().getVariables().get(ORDER_HEADER);
        String payment = context.getMessage().getHeaders().get(PAYMENT_HEADER, String.class);
        log.info("Paid [amount = {}}] for order with [order details = {}]", payment, orderDto);
        if(orderDto==null) throw new OrderIdNotFoundException();
        if(Double.valueOf(payment).equals(orderDto.getTotalAmount()))  {
            orderDto = updateAmountPaid(orderDto.getUuid(), Double.valueOf(payment));
        }
        context.getStateMachine().getExtendedState().getVariables().put(ORDER_HEADER, orderDto);
    }

    @SneakyThrows(ApplicationException.class)
    private OrderDto updateAmountPaid(String uuid, Double amountPaid) {
        Order _order = orderService.get(uuid);
        _order.setAmountPaid(amountPaid);
        _order.setStatus(OrderStates.PAID.name());
        orderService.save(_order);
        return OrderDto.builder().build().createDtoFromEntity(_order);
    }

}