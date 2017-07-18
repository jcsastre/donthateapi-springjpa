package springjpatests.springjpatests.controller;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import springjpatests.springjpatests.model.Customer;
import springjpatests.springjpatests.service.CustomerService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author: Juan Carlos Sastre
 */
public class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private List<Customer> customers;
    private static final int GENERATED_CUSTOMERS_COUNT = 5;

    private final Faker faker = new Faker();

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        mockMvc =
            MockMvcBuilders
                .standaloneSetup(customerController)
                .build();

        customers =
            Stream
                .generate(
                    ()-> new Customer(
                            UUID.randomUUID(),
                            faker.name().firstName(),
                            faker.name().lastName()
                    )
                )
                .limit(GENERATED_CUSTOMERS_COUNT)
                .collect(Collectors.toList());
    }

    @Test
    public void test_getAllCustomers() throws Exception {

        // Configure mock object to return the test data when
        // the getAllCustomers() method of the customerRepository is invoked.
        when(customerService.findAll()).thenReturn(customers);

        // Invoke an HTTP GET request to the /customers URI.
        mockMvc.perform(get("/customers"))
            // Verify that the HTTP status code is 200 (OK).
            .andExpect(status().isOk())
            // Verify that the content-type of the response is application/json and its character set is UTF-8
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            // Verify that the collection contains GENERATED_CUSTOMERS_COUNT items
            .andExpect(jsonPath("$", hasSize(GENERATED_CUSTOMERS_COUNT)))
            // Verify that the id attribute of the first element is correct
            .andExpect(jsonPath("$[0].id", is(customers.get(0).getId().toString())))
            // Verify that the firstName attribute of the first element is correct.
            .andExpect(jsonPath("$[0].firstName", is(customers.get(0).getFirstName())));

        // Verify that the findAll() method of the UserService is invoked exactly once
        verify(customerService, times(1)).findAll();
        // Verify that after the response, no more interactions are made to the userService
        verifyNoMoreInteractions(customerService);
    }

    @Test
    public void test_getOneCustomer() throws Exception {

        final Customer customer = customers.get(0);

        when(customerService.findOne(customer.getId())).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/customers/{id}", customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id", is(customer.getId().toString())))
            .andExpect(jsonPath("$.firstName", is(customer.getFirstName())));

        verify(customerService, times(1)).findOne(customer.getId());
        verifyNoMoreInteractions(customerService);
    }

//
//    @Test
//    public void test_createOneCustomer() throws Exception {
//        assert true;
//    }
}
