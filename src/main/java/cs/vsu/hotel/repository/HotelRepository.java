package cs.vsu.hotel.repository;

import cs.vsu.hotel.entity.Hotel;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface HotelRepository extends PagingAndSortingRepository<Hotel, Long> {
}
