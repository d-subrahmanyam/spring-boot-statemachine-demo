package io.subbu.mock.services;


import io.subbu.mock.generators.ProductMockGenerator;
import io.subbu.models.Product;
import io.subbu.repos.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Order(2)
@Component
@Slf4j
public class MockProductService implements CommandLineRunner {

    @Autowired
    private ProductMockGenerator productMockGenerator;

    @Autowired
    private ProductRepo productRepo;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("Started creating mock products...");
        IntStream.range(1, 10).forEach(i -> {
            Product _product = productMockGenerator.generate();
            log.debug("Creating product - {}", _product);
            productRepo.save(_product);
        });
        log.info("Finished creating mock products...");
    }
}
