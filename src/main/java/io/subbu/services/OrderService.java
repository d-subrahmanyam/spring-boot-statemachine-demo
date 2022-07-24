package io.subbu.services;

import io.subbu.contracts.ServiceI;
import io.subbu.exceptions.ApplicationException;
import io.subbu.exceptions.OrderNotFoundException;
import io.subbu.exceptions.ProductNotFoundException;
import io.subbu.models.Order;
import io.subbu.models.Product;
import io.subbu.repos.OrderRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Slf4j
public class OrderService implements ServiceI<Order> {

    @Autowired
    private OrderRepo orderRepo;

    /**
     * Fetch all the entities
     *
     * @return returns a collection of the entities
     */
    @Override
    public List<Order> all() {
        return orderRepo.findAll();
    }

    /**
     * Fetch the entity given its UUID
     *
     * @param uuid UUID of the entity
     * @return returns the entity
     * @throws OrderNotFoundException
     */
    @Override
    public Order get(String uuid) throws ApplicationException {
        Order order = null;
        try {
            order = orderRepo.findByUuid(uuid);
        } catch (Exception ex) {
            if(ex instanceof EntityNotFoundException) {
                throw new OrderNotFoundException();
            }
        }
        return order;
    }

    /**
     * Save/Update the given entity
     *
     * @param order given entity
     */
    @Override
    public void save(Order order) {
        orderRepo.save(order);
    }

    /**
     * Delete the entity for the given UUID
     *
     * @param uuid UUID of the entity
     * @throws ApplicationException
     */
    @Override
    public void delete(String uuid) throws ApplicationException {
        try {
            orderRepo.delete(get(uuid));
        } catch (Exception ex) {
            if(ex instanceof ApplicationException) {
                throw new OrderNotFoundException();
            }
        }
    }
}
