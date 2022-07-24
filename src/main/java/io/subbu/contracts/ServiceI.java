package io.subbu.contracts;

import io.subbu.exceptions.ApplicationException;

import java.util.List;

public interface ServiceI<T> {

    /**
     * Fetch all the entities
     * @return returns a collection of the entities
     */
    List<T> all();

    /**
     * Fetch the entity given its UUID
     * @param uuid UUID of the entity
     * @return returns the entity
     * @throws ApplicationException
     */
    T get(String uuid) throws ApplicationException;

    /**
     * Save/Update the given entity
     * @param t given entity
     */
    void save(T t);

    /**
     * Delete the entity for the given UUID
     * @param uuid UUID of the entity
     * @throws ApplicationException
     */
    void delete(String uuid) throws ApplicationException;

}
