package pl.booking.db.repo;

import org.springframework.data.repository.CrudRepository;
import pl.booking.db.entity.Address;

/**
 * Created by Szymon on 1/6/2016.
 */
public interface AddressRepository extends CrudRepository<Address, Long> {
}
