package cs.vsu.hotel.service;

import cs.vsu.hotel.entity.Amenities;
import cs.vsu.hotel.repository.AmenitiesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AmenitiesService {
    private final AmenitiesRepository amenitiesRepository;

    public AmenitiesService(AmenitiesRepository amenitiesRepository) {
        this.amenitiesRepository = amenitiesRepository;
    }

    public Amenities findById(Long id) {
        return amenitiesRepository.findById(id).orElse(null);
    }

    public List<Amenities> findAll() {
        List<Amenities> amenities = new ArrayList<>(amenitiesRepository.findAll());
        amenities.sort(Comparator.comparing(Amenities::getId));
        return amenities;
    }

    public Amenities saveAmenities(Amenities amenities) {
        return amenitiesRepository.save(amenities);
    }

    public void deleteAmenities(Amenities amenities) {
        amenitiesRepository.delete(amenities);
    }

    public Amenities findByName(String name) {
        return amenitiesRepository.findByName(name).orElse(null);
    }
}
