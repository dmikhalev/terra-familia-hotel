package cs.vsu.hotel.repository;

import cs.vsu.hotel.entity.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface RoomRepository extends PagingAndSortingRepository<Room, Long> {

    List<Room> findAll();

    Room getRoomByNumber(Integer number);

    @Query("SELECT r FROM Room r " +
            "WHERE r.adultCount >= :adultCount AND r.childCount >= :childCount " +
            "AND (SELECT COUNT(b) FROM Booking b WHERE b.room = r AND NOT (b.startDate > :endDate OR b.endDate < :startDate)) = 0")
    List<Room> findUnbookedRooms(@Param("startDate") Date startDate,
                                 @Param("endDate") Date endDate,
                                 @Param("adultCount") int adultCount,
                                 @Param("childCount") int childCount);
}
