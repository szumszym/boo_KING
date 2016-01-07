package pl.booking.db.repo;

import org.springframework.data.repository.CrudRepository;
import pl.booking.db.entity.Reservation;

/**
 * Created by Szymon on 1/6/2016.
 */
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
}
