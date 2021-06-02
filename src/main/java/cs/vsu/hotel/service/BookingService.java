package cs.vsu.hotel.service;

import cs.vsu.hotel.entity.Booking;
import cs.vsu.hotel.entity.Payment;
import cs.vsu.hotel.repository.BookingRepository;
import cs.vsu.hotel.util.DateUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final PaymentService paymentService;

    public BookingService(BookingRepository bookingRepository, @Lazy PaymentService paymentService) {
        this.bookingRepository = bookingRepository;
        this.paymentService = paymentService;
    }

    public Booking findById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public List<Booking> findAll() {
        return new ArrayList<>(bookingRepository.findAll());
    }

    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public boolean deleteBooking(Booking booking) {
        if (booking != null) {
            Payment payment = booking.getPayment();
            if (payment != null) {
                paymentService.delete(payment);
            }
            bookingRepository.delete(booking);
            return true;
        }
        return false;
    }

    public List<Booking> getUnpaidBookingsByPassportId(Long passportId) {
        return new ArrayList<>(bookingRepository.getUnpaidBookingsByPassportId(passportId));
    }

    public List<Booking> getPaidBookingsByPassportId(Long passportId) {
        return new ArrayList<>(bookingRepository.getPaidBookingsByPassportId(passportId));
    }

    public List<Booking> getAllUnpaidBookings() {
        return new ArrayList<>(bookingRepository.getAllUnpaidBookings());
    }

    public List<Booking> getAllPaidBookings() {
        return new ArrayList<>(bookingRepository.getAllPaidBookings());
    }
}
