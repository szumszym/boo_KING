package pl.booking.db.repo;

import org.springframework.data.repository.CrudRepository;
import pl.booking.db.entity.Status;

/**
 * Created by Szymon on 1/6/2016.
 */
public interface StatusRepository extends CrudRepository<Status, Long> {
}
