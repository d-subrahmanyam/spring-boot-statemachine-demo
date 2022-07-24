package io.subbu.dtos;

import io.subbu.contracts.DtoI;
import io.subbu.models.Order;
import io.subbu.models.OrderLineItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class OrderDto implements DtoI<Order, OrderDto> {

    private Set<OrderLineItemDto> orderLineItemDtos;

    private Double totalAmount;

    private Double amountPaid;

    private String uuid;

    private String status;

    @Override
    public Order createEntity() {
        return Order.builder()
                .lineItems(fromDto())
                .totalAmount(this.totalAmount)
                .amountPaid(this.amountPaid)
                .uuid(this.uuid)
                .status(this.status)
                .build();
    }

    @Override
    public OrderDto createDtoFromEntity(Order order) {
        return OrderDto.builder()
                .orderLineItemDtos(toDtos(order.getLineItems()))
                .totalAmount(order.getTotalAmount())
                .amountPaid(order.getAmountPaid())
                .uuid(order.getUuid())
                .status(order.getStatus())
                .build();
    }

    private Set<OrderLineItem> fromDto() {
        Set<OrderLineItem> orderLineItems = new HashSet<>();
        for(OrderLineItemDto orderLineItemDto: this.orderLineItemDtos) {
            orderLineItems.add(orderLineItemDto.createEntity());
        }
        return orderLineItems;
    }

    private Set<OrderLineItemDto> toDtos(Set<OrderLineItem> orderLineItems) {
        Set<OrderLineItemDto> _orderLineItemDtos = new HashSet<>();
        for(OrderLineItem _orderLineItem: orderLineItems) {
            _orderLineItemDtos.add(OrderLineItemDto.builder().build().createDtoFromEntity(_orderLineItem));
        }
        return _orderLineItemDtos;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
