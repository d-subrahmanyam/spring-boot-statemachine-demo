package io.subbu.dtos;

import io.subbu.contracts.DtoI;
import io.subbu.models.OrderLineItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class OrderLineItemDto implements DtoI<OrderLineItem, OrderLineItemDto> {

    private ProductDto productDto;

    private int quantity;

    @Override
    public OrderLineItem createEntity() {
        return OrderLineItem.builder()
                .product(this.productDto.createEntity())
                .quantity(this.quantity)
                .build();
    }

    @Override
    public OrderLineItemDto createDtoFromEntity(OrderLineItem orderLineItem) {
        return OrderLineItemDto.builder()
                .productDto(ProductDto.builder().build().createDtoFromEntity(orderLineItem.getProduct()))
                .quantity(orderLineItem.getQuantity())
                .build();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
