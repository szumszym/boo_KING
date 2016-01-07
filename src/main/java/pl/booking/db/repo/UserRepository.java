package pl.booking.db.repo;

import org.springframework.data.repository.CrudRepository;
import pl.booking.db.entity.User;

import java.util.List;

/**
 * Created by Szymon on 1/6/2016.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByFirstName(String firstName);

    User findByEmail(String email);
}
