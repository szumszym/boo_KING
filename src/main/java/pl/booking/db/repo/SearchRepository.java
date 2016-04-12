package pl.booking.db.repo;

import org.springframework.data.jpa.repository.Query;
import pl.booking.reflection.CustomRepository;

import java.util.List;

/**
 * Created by Szymon on 4/11/2016.
 */
public interface SearchRepository extends CustomRepository {

    @Query("select distinct a.city from Hotel h join h.address a")
    List<String> findAllCities();

    @Query("select distinct a.country from Hotel h join h.address a")
    List<String> findAllCountries();

    @Query("select count(*) from Hotel h join h.address a")
    Long findNumberOfHotels();

    @Query("select avg(a.apartmentNo) from Address a")
    Double findAvgApartmentNo();
}
