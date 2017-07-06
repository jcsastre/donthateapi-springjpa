package springjpatests.springjpatests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import springjpatests.springjpatests.repository.CustomerRepository;
import springjpatests.springjpatests.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author: Juan Carlos Sastre
 */

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> getAllCustomers() {

        final List<Customer> customers = customerRepository.findAll();

        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Customer> getOneCustomer(@PathVariable String id) {

        final Customer customer = customerRepository.findOne(Long.valueOf(id));

        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> create(
        @RequestBody Customer customer,
        UriComponentsBuilder ucBuilder
    ) {
        customerRepository.save(customer);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(
            ucBuilder.path("/customers/{id}").buildAndExpand(customer.getId()).toUri()
        );

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }
}
