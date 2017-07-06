package springjpatests.springjpatests.service.impl;

import org.springframework.stereotype.Service;
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
    public Customer save(Customer customer) {
        return
            customerRepository.save(customer);
    }

    @Override
    public boolean exists(Customer customer) {

        return
            findOne(customer.getId()).isPresent();
    }
}
