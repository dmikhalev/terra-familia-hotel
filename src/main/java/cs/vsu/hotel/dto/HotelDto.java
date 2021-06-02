package cs.vsu.hotel.dto;

import cs.vsu.hotel.entity.Hotel;
import lombok.Data;

@Data
public class HotelDto {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String description;

    public HotelDto() {

    }

    public HotelDto(Long id, String name, String address, String phoneNumber, String email, String description) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.description = description;
    }

    public Hotel toHotel() {
        Hotel hotel = new Hotel();
        hotel.setId(id);
        hotel.setName(name);
        hotel.setAddress(address);
        hotel.setEmail(email);
        hotel.setPhoneNumber(phoneNumber);
        return hotel;
    }

    public static HotelDto fromHotel(Hotel hotel) {
        return new HotelDto(
                hotel.getId(),
                hotel.getName(),
                hotel.getAddress(),
                hotel.getPhoneNumber(),
                hotel.getEmail(),
                hotel.getDescription()
        );
    }

}
