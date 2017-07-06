package springjpatests.springjpatests.dto;

import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

/**
 * @author: Juan Carlos Sastre
 */
@Value public class CustomerRestDto {
    private String firstName;
}
