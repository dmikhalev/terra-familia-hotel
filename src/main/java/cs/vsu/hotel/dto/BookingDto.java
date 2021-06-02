package cs.vsu.hotel.dto;

import cs.vsu.hotel.entity.Booking;
import cs.vsu.hotel.entity.Client;
import cs.vsu.hotel.entity.Room;
import cs.vsu.hotel.util.DateUtils;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


@Data
public class BookingDto {

    private static final Logger logger = LoggerFactory.getLogger(BookingDto.class);

    private Long bookingId;
    private String startDate;
    private String endDate;
    private Integer roomNumber;
    private Integer clientCount;
    private String firstName;
    private String lastName;
    private String passportId;
    private Integer age;
    private String gender;
    private String phoneNumber;
    private String email;
    private Integer price;
    private Date paymentDate;

    public BookingDto(String startDate, String endDate, Integer roomNumber, Integer clientCount, String firstName,
                      String lastName, String passportId, Integer age, String gender, String phoneNumber, String email) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.roomNumber = roomNumber;
        this.clientCount = clientCount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passportId = passportId;
        this.age = age;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public BookingDto(Long bookingId, String startDate, String endDate, Integer roomNumber, String firstName,
                      String lastName, String passportId, String phoneNumber, String email, Integer price) {
        this.bookingId = bookingId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.roomNumber = roomNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passportId = passportId;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.price = price;
    }

    public BookingDto() {

    }

    public static BookingDto toBookingDto(Booking booking) {
        Client client = booking.getClient();
        Room room = booking.getRoom();
        BookingDto bookingDto = new BookingDto(
                booking.getId(),
                DateUtils.dateToDdMmYyyyStr(booking.getStartDate()),
                DateUtils.dateToDdMmYyyyStr(booking.getEndDate()),
                room.getNumber(),
                client.getFirstName(),
                client.getLastName(),
                String.valueOf(client.getPassportId()),
                client.getPhoneNumber(),
                client.getEmail(),
                booking.getPrice());
        if (booking.getPayment() != null) {
            bookingDto.setPaymentDate(booking.getPayment().getPaymentDate());
        }
        return bookingDto;
    }

    public Date getFormatStartDate() {
        return DateUtils.parseDate(startDate);
    }

    public Date getFormatEndDate() {
        return DateUtils.parseDate(endDate);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Long getPassportIdL() {
        Long passportId = null;
        try {
            passportId = Long.parseLong(this.passportId.replace(" ", ""));
        } catch (NumberFormatException e) {
            logger.error("Incorrect input passport id", e);
        }
        return passportId;
    }
}
