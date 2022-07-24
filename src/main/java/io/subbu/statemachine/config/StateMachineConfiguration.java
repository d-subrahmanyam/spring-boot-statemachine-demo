package io.subbu.statemachine.config;

import io.subbu.statemachine.actions.*;
import io.subbu.statemachine.constants.OrderEvents;
import io.subbu.statemachine.constants.OrderStates;
import io.subbu.statemachine.listeners.StateMachineListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class StateMachineConfiguration extends EnumStateMachineConfigurerAdapter<OrderStates, OrderEvents> {

    @Override
    public void configure(StateMachineStateConfigurer<OrderStates, OrderEvents> states) throws Exception {
        states.withStates().initial(OrderStates.SUBMITTED)
                .state(OrderStates.CREATE)
                .state(OrderStates.PAID)
                .end(OrderStates.FULFILLED)
                .end(OrderStates.CANCELLED);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStates, OrderEvents> config) throws Exception {
        config.withConfiguration()
                .autoStartup(false)
                .listener(new StateMachineListener());
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStates, OrderEvents> transitions) throws Exception {
        transitions
                .withExternal().source(OrderStates.SUBMITTED)
                .target(OrderStates.CREATE)
                .event(OrderEvents.CREATE_ORDER)
                .action(createOrderAction(), errorAction())
                .and()
                .withExternal().source(OrderStates.CREATE)
                .target(OrderStates.PAID)
                .event(OrderEvents.PAY)
                .action(paymentAction(), errorAction())
                .and()
                .withExternal().source(OrderStates.PAID)
                .target(OrderStates.FULFILLED)
                .event(OrderEvents.FULFILL)
                .action(fullfillAction(), errorAction())
                .and()
                .withExternal().source(OrderStates.SUBMITTED)
                .target(OrderStates.CANCELLED)
                .event(OrderEvents.CANCEL)
                .action(cancelAction(), errorAction())
                .and()
                .withExternal().source(OrderStates.PAID)
                .target(OrderStates.CANCELLED)
                .event(OrderEvents.CANCEL)
                .action(cancelAction(), errorAction())
                .and()
                .withExternal().source(OrderStates.FULFILLED)
                .target(OrderStates.CANCELLED)
                .event(OrderEvents.CANCEL)
                .action(cancelAction(), errorAction());
    }

    @Bean
    public PaymentAction paymentAction() {
        return new PaymentAction();
    }

    @Bean
    public ErrorAction errorAction() {
        return new ErrorAction();
    }

    @Bean
    public FulfillAction fullfillAction() {
        return new FulfillAction();
    }

    @Bean
    public CancelAction cancelAction() {
        return new CancelAction();
    }

    @Bean
    public CreateOrderAction createOrderAction() {
        return new CreateOrderAction();
    }

    @Bean
    StateMachine<OrderStates, OrderEvents> stateMachine() throws Exception {
        StateMachineBuilder.Builder<OrderStates, OrderEvents> builder = StateMachineBuilder.builder();
        builder.configureStates()
                .withStates()
                .initial(OrderStates.SUBMITTED)
                .end(OrderStates.FULFILLED)
                .end(OrderStates.CANCELLED)
                .states(EnumSet.allOf(OrderStates.class));
        return builder.build();
    }
}