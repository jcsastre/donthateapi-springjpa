package springjpatests.springjpatests.service;

import springjpatests.springjpatests.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author: Juan Carlos Sastre
 */
public interface CustomerService {

    Optional<Customer> findOne(UUID id);

    List<Customer> findAll();

    Customer save(Customer customer);

    boolean exists(UUID id);
}
