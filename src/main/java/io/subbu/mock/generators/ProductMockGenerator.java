package io.subbu.mock.generators;

import com.github.javafaker.Faker;
import io.subbu.contracts.MockI;
import io.subbu.models.Product;
import io.subbu.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ProductMockGenerator implements MockI<Product> {

    @Autowired
    private Faker faker;

    private List<String> _measurements = List.of("KGS", "LTRS", "DOZENS", "NOS");

    @Override
    public Product generate() {
        return Product.builder()
                .name(faker.commerce().productName())
                .price(Double.valueOf(faker.commerce().price()))
                .measurement(_measurements.get(faker.random().nextInt(0, 3)))
                .uuid(UUID.randomUUID().toString())
                .build();
    }
}
