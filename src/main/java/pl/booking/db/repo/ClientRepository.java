package pl.booking.db.repo;

import org.springframework.data.repository.CrudRepository;
import pl.booking.db.entity.Client;

/**
 * Created by Szymon on 1/6/2016.
 */
public interface ClientRepository extends CrudRepository<Client, Long> {
}
