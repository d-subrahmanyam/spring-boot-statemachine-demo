package io.subbu.services;

import io.subbu.contracts.ServiceI;
import io.subbu.exceptions.ApplicationException;
import io.subbu.exceptions.UserNotFoundException;
import io.subbu.models.User;
import io.subbu.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserService implements ServiceI<User> {

    @Autowired
    private UserRepo userRepo;

    /**
     * Fetch all the entities
     *
     * @return returns a collection of the entities
     */
    @Override
    public List<User> all() {
        return userRepo.findAll();
    }

    /**
     * Fetch the entity given its UUID
     *
     * @param uuid UUID of the entity
     * @return returns the entity
     * @throws ApplicationException
     */
    @Override
    public User get(String uuid) throws ApplicationException {
        User user = null;
        try {
            user = userRepo.findByUuid(uuid);
        } catch (Exception ex) {
            if(ex instanceof EntityNotFoundException) {
                throw new UserNotFoundException();
            }
        }
        return user;
    }

    /**
     * Save/Update the given entity
     *
     * @param user given entity
     */
    @Override
    public void save(User user) {
        userRepo.save(user);
    }

    /**
     * Delete the entity for the given UUID
     *
     * @param uuid UUID of the entity
     */
    @Override
    public void delete(String uuid) throws ApplicationException {
        try {
            userRepo.delete(get(uuid));
        } catch (Exception ex) {
            if(ex instanceof UserNotFoundException) {
                throw new UserNotFoundException();
            }
        }
    }
}
