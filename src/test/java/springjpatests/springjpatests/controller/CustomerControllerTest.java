package springjpatests.springjpatests.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import springjpatests.springjpatests.model.Customer;
import springjpatests.springjpatests.repository.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

/**
 * @author: Juan Carlos Sastre
 */
public class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerController customerController;

    private List<Customer> customers;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        mockMvc =
            MockMvcBuilders
                .standaloneSetup(customerController)
                .build();

        customers =
            Stream
                .generate(()-> new Customer("Juan Carlos"))
                .limit(5)
                .collect(Collectors.toList());
    }

    @Test
    public void test_getAllCustomers() throws Exception {

        when(customerRepository.findAll()).thenReturn(customers);

        
    }
}
