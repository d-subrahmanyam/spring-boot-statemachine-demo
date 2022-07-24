package io.subbu.mock.services;


import io.subbu.mock.generators.UserMockGenerator;
import io.subbu.models.User;
import io.subbu.repos.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Order(1)
@Component
@Slf4j
public class MockUserService implements CommandLineRunner {

    @Autowired
    private UserMockGenerator userMockGenerator;

    @Autowired
    private UserRepo userRepo;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("Started creating mock users...");
        IntStream.range(1, 10).forEach(i -> {
            User _user = userMockGenerator.generate();
            log.debug("Creating user - {}", _user);
            userRepo.save(_user);
        });
        log.info("Finished creating mock users...");
    }
}
