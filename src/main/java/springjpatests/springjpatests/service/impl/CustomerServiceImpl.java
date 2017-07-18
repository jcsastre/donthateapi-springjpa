package springjpatests.springjpatests.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import springjpatests.springjpatests.model.Customer;
import springjpatests.springjpatests.repository.CustomerRepository;
import springjpatests.springjpatests.service.CustomerService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author: Juan Carlos Sastre
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Customer> findOne(UUID id) {
        return
            Optional.ofNullable(
                customerRepository.findOne(id)
            );
    }

    @Override
    public List<Customer> findAll() {
        return
            customerRepository.findAll();
    }

    @Override
    public Customer save(String firstName, String lastName) {

        Assert.notNull(firstName, "firstName is mandatory.");
        Assert.notNull(lastName, "lastName is mandatory.");

        UUID validUuid = null;
        while (validUuid == null) {
            UUID uuid = UUID.randomUUID();
            if (exists(uuid)) {
                validUuid = uuid;
            }
        }

        return
            customerRepository.save(
                new Customer(
                    validUuid,
                    firstName,
                    lastName
                )
            );
    }

    @Override
    public boolean exists(UUID id) {

        return
            findOne(id).isPresent();
    }
}
