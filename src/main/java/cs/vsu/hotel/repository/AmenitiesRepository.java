package cs.vsu.hotel.repository;

import cs.vsu.hotel.entity.Amenities;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface AmenitiesRepository extends PagingAndSortingRepository<Amenities, Long> {
    List<Amenities> findAll();

    Optional<Amenities> findByName(String name);
}
