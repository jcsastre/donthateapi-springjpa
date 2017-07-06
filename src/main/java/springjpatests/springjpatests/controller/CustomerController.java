package springjpatests.springjpatests.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import springjpatests.springjpatests.dto.CustomerRestDto;
import springjpatests.springjpatests.model.Customer;
import springjpatests.springjpatests.service.CustomerService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author: Juan Carlos Sastre
 */

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> getAllCustomers() {

        final List<Customer> customers = customerService.findAll();

        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Customer> getOneCustomer(@PathVariable String id) {

        final Optional<Customer> optionalCustomer = customerService.findOne(UUID.fromString(id));

        return optionalCustomer.map(
            customer -> new ResponseEntity<>(customer, HttpStatus.OK)
        ).orElseGet(
            () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createOneCustomer(
        @RequestBody CustomerRestDto customerRestDto,
        UriComponentsBuilder ucBuilder
    ) {

        //TODO: this code should not be in the controller
        UUID validUuid = null;
        while (validUuid == null) {
            UUID uuid = UUID.randomUUID();
            if (!customerService.exists(uuid)) {
                validUuid = uuid;
            }
        }

        final Customer customer = new Customer(
            validUuid,
            customerRestDto.getFirstName()
        );

        customerService.save(customer);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(
            ucBuilder.path("/customers/{id}").buildAndExpand(customer.getId()).toUri()
        );

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }
}
