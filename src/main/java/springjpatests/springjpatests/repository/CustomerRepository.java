package springjpatests.springjpatests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import springjpatests.springjpatests.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author: Juan Carlos Sastre
 */
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
