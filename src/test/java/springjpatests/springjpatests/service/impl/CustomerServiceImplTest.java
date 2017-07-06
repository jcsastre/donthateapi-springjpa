package springjpatests.springjpatests.service.impl;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import springjpatests.springjpatests.model.Customer;
import springjpatests.springjpatests.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.npathai.hamcrestopt.OptionalMatchers.hasValue;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresent;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author: Juan Carlos Sastre
 */
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    private List<Customer> customers;
    private static final int GENERATED_CUSTOMERS_COUNT = 5;

    private final Faker faker = new Faker();

    private CustomerServiceImpl customerServiceImpl;

    private UUID uuidThatNotExists;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        customers =
            Stream
                .generate(
                    ()-> new Customer(UUID.randomUUID(), faker.name().firstName())
                )
                .limit(GENERATED_CUSTOMERS_COUNT)
                .collect(Collectors.toList());

        customerServiceImpl = new CustomerServiceImpl(customerRepository);

        uuidThatNotExists = null;
        while (uuidThatNotExists == null) {
            final UUID randomUUID = UUID.randomUUID();
            boolean anyMatch =
                customers.stream()
                    .anyMatch(
                        customer -> customer.getId().equals(randomUUID)
                    );
            if (!anyMatch) {
                uuidThatNotExists = randomUUID;
            }
        }
    }

    @Test
    public void test_findOne() {

        final Customer customer = customers.get(0);
        when(customerRepository.findOne(customer.getId())).thenReturn(customer);

        final Optional<Customer> optionalCustomer = customerServiceImpl.findOne(customer.getId());

        // Hamcrest Optional (https://github.com/npathai/hamcrest-optional)
        assertThat(optionalCustomer, isPresent());
        assertThat(optionalCustomer, hasValue(customer));
    }

    @Test
    public void test_findAll() {

        when(customerRepository.findAll()).thenReturn(customers);

        final List<Customer> allCustomers = customerServiceImpl.findAll();

        assertThat(allCustomers, is(customers));
        assertThat(allCustomers, hasSize(GENERATED_CUSTOMERS_COUNT));
    }

    @Test
    public void test_exists() {

        final Customer existingCustomer = customers.get(0);
        final UUID uuidThatExists = existingCustomer.getId();

        when(customerRepository.findOne(uuidThatExists)).thenReturn(existingCustomer);
        when(customerRepository.findOne(uuidThatNotExists)).thenReturn(null);

        assertTrue(customerServiceImpl.exists(uuidThatExists));
        assertFalse(customerServiceImpl.exists(uuidThatNotExists));
    }
}
