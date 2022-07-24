package io.subbu.dtos;

import io.subbu.contracts.DtoI;
import io.subbu.models.Product;
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
public class ProductDto implements DtoI<Product, ProductDto> {

    private String name;
    private Double price;
    private String measurement;
    private String uuid;

    @Override
    public Product createEntity() {
        return Product.builder()
                .name(this.name)
                .price(this.price)
                .measurement(this.measurement)
                .uuid(this.uuid)
                .build();
    }

    @Override
    public ProductDto createDtoFromEntity(Product product) {
        return ProductDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .measurement(product.getMeasurement())
                .uuid(product.getUuid())
                .build();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
