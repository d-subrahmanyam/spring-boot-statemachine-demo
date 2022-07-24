package io.subbu.statemachine.listeners;

import io.subbu.statemachine.constants.OrderEvents;
import io.subbu.statemachine.constants.OrderStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import static java.lang.String.format;

@Slf4j
public class StateMachineListener extends StateMachineListenerAdapter<OrderStates, OrderEvents> {

    @Override
    public void stateChanged(State<OrderStates, OrderEvents> from, State<OrderStates, OrderEvents> to) {
        if (from != null) {
            log.info(format("state changed from %s to %s", from.getId(), to.getId()));
        } else {
            log.info("the from action state seems to be null, the order could have just been SUBMITTED");

        }
    }
}