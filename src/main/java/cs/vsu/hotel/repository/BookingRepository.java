package cs.vsu.hotel.repository;

import cs.vsu.hotel.entity.Booking;
import cs.vsu.hotel.entity.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BookingRepository extends PagingAndSortingRepository<Booking, Long> {

    List<Booking> findBookingByRoom(Room room);

    List<Booking> findAll();

    @Query("SELECT b FROM Booking b " +
            "WHERE b.client.passportId = :passportId " +
            "AND b.payment IS NULL " +
            "ORDER BY b.startDate")
    List<Booking> getUnpaidBookingsByPassportId(Long passportId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.client.passportId = :passportId " +
            "AND b.payment IS NOT NULL " +
            "ORDER BY b.payment.paymentDate")
    List<Booking> getPaidBookingsByPassportId(Long passportId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.payment IS NULL " +
            "ORDER BY b.startDate")
    List<Booking> getAllUnpaidBookings();

    @Query("SELECT b FROM Booking b " +
            "WHERE b.payment IS NOT NULL " +
            "ORDER BY b.payment.paymentDate")
    List<Booking> getAllPaidBookings();
}
