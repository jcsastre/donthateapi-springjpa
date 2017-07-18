package springjpatests.springjpatests.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

/**
 * @author: Juan Carlos Sastre
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Customer {

    @Id
    private UUID id;

    @NonNull private String firstName;
    @NonNull private String lastName;
}
