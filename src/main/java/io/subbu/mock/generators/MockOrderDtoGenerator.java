package io.subbu.mock.generators;

import com.github.javafaker.Faker;
import io.subbu.contracts.MockI;
import io.subbu.dtos.OrderDto;
import io.subbu.dtos.OrderLineItemDto;
import io.subbu.dtos.ProductDto;
import io.subbu.models.Order;
import io.subbu.models.OrderLineItem;
import io.subbu.models.Product;
import io.subbu.services.ProductService;
import io.subbu.statemachine.constants.OrderStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

@Component
@Slf4j
public class MockOrderDtoGenerator implements MockI<OrderDto> {

    @Autowired
    private ProductService productService;

    @Autowired
    private Faker faker;

    @Override
    public OrderDto generate() {
        Set<OrderLineItemDto> orderLineItemDtos = getOrderLineItemDtos();
        OrderDto orderDto = OrderDto.builder()
                .orderLineItemDtos(orderLineItemDtos)
                .uuid(UUID.randomUUID().toString())
                .totalAmount(getTotalAmount(orderLineItemDtos))
                .status(OrderStates.SUBMITTED.name())
                .build();
        log.info("Order generated - \n{}", orderDto);
        return orderDto;
    }

    private ProductDto getProductDto() {
        Product product =  productService.all().get(faker.random().nextInt(0, 8));
        return ProductDto.builder().build().createDtoFromEntity(product);
    }

    private Set<OrderLineItemDto> getOrderLineItemDtos() {
        Set<OrderLineItemDto> orderLineItemDtos = new HashSet<>();
        IntStream.range(1, 3).forEach(i -> {
            OrderLineItemDto orderLineItemDto = OrderLineItemDto.builder()
                    .productDto(getProductDto())
                    .quantity(4)
                    .build();
            orderLineItemDtos.add(orderLineItemDto);
        });
        return orderLineItemDtos;
    }

    private Double getTotalAmount(Set<OrderLineItemDto> orderLineItemDtos) {
        AtomicReference<Double> totalAmount = new AtomicReference<>((double) 0);
        orderLineItemDtos.stream()
                .forEach(orderLineItemDto -> {
                    totalAmount.updateAndGet(v -> (double) (v + orderLineItemDto.getProductDto().getPrice().doubleValue()));
                });
        return totalAmount.get();
    }

}
