package cs.vsu.hotel.service;

import cs.vsu.hotel.entity.Hotel;
import cs.vsu.hotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class HotelService {
    private static final long HOTEL_ID = 1L;

    private final HotelRepository hotelRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Hotel getHotel() {
        return hotelRepository.findById(HOTEL_ID).orElse(null);
    }

    public Hotel findById(Long id) {
        return hotelRepository.findById(id).orElse(null);
    }

    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

}
