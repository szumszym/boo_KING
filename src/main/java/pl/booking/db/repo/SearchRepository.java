package pl.booking.db.repo;

import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * Created by Szymon on 4/11/2016.
 */
public interface SearchRepository {

    @Query("select distinct a.city from Hotel h join h.address a")
    List<String> findAllCities();

    @Query("select distinct a.country from Hotel h join h.address a")
    List<String> findAllCountries();

    @Query("select count(*) from Hotel h join h.address a")
    Long findNumberOfHotels();

    @Query("select avg(a.apartmentNo) from Address a")
    Double findAvgApartmentNo();

    @Query("select a.country, count(a.city) from Address a group by a.country order by count(a.city) desc")
    Map<String, Long> findCityCountByCountry();
}
