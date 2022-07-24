package io.subbu.components;

import io.subbu.statemachine.constants.OrderEvents;
import io.subbu.statemachine.constants.OrderStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

@Order(21)
@Component
public class PlaceOrder implements CommandLineRunner {

    @Autowired
    private StateMachine<OrderStates, OrderEvents> stateMachine;

    @Override
    public void run(String... args) throws Exception {
        stateMachine.start();
    }

}
