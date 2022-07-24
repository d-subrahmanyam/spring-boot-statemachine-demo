package io.subbu.mock.generators;

import com.github.javafaker.Faker;
import io.subbu.contracts.MockI;
import io.subbu.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMockGenerator implements MockI<User> {

    @Autowired
    private Faker faker;

    @Override
    public User generate() {
        return User.builder()
                .username(faker.name().username())
                .password("pa55ward")
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .uuid(UUID.randomUUID().toString())
                .build();
    }
}
