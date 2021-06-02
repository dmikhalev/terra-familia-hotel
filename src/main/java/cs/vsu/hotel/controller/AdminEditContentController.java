package cs.vsu.hotel.controller;

import cs.vsu.hotel.dto.AmenitiesDto;
import cs.vsu.hotel.dto.HotelDto;
import cs.vsu.hotel.dto.RoomDto;
import cs.vsu.hotel.entity.Amenities;
import cs.vsu.hotel.entity.Hotel;
import cs.vsu.hotel.entity.Room;
import cs.vsu.hotel.service.AmenitiesService;
import cs.vsu.hotel.service.HotelService;
import cs.vsu.hotel.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/edit")
public class AdminEditContentController {

    private final HotelService hotelService;
    private final RoomService roomService;
    private final AmenitiesService amenitiesService;

    public AdminEditContentController(HotelService hotelService, RoomService roomService, AmenitiesService amenitiesService) {
        this.hotelService = hotelService;
        this.roomService = roomService;
        this.amenitiesService = amenitiesService;
    }

    @GetMapping()
    public String getMainPage(Model model) {
        Hotel hotel = hotelService.getHotel();
        model.addAttribute("hotel", HotelDto.fromHotel(hotel));

        Map<Integer, Room> rooms = roomService.getNumberToRoom();
        model.addAttribute("room1", rooms.get(1));
        model.addAttribute("room2", rooms.get(2));
        model.addAttribute("room3", rooms.get(3));
        model.addAttribute("room4", rooms.get(4));

        AmenitiesDto amenities = getAmenitiesDto();
        model.addAttribute("amenitiesDto", amenities);

        return "admin/edit";
    }

    private AmenitiesDto getAmenitiesDto() {
        AmenitiesDto result = new AmenitiesDto();
        result.setAmenitiesRows(new ArrayList<>());
        List<Amenities> amenities = amenitiesService.findAll();
        amenities.forEach(a -> {
            AmenitiesDto.AmenitiesRow amenitiesRow = new AmenitiesDto.AmenitiesRow();
            amenitiesRow.setName(a.getName());
            amenitiesRow.setId(a.getId());
            a.getRooms().forEach(r -> {
                switch (r.getNumber()) {
                    case 1:
                        amenitiesRow.setInRoom1(true);
                        break;
                    case 2:
                        amenitiesRow.setInRoom2(true);
                        break;
                    case 3:
                        amenitiesRow.setInRoom3(true);
                        break;
                    case 4:
                        amenitiesRow.setInRoom4(true);
                        break;
                }
            });
            result.getAmenitiesRows().add(amenitiesRow);
        });
        return result;
    }

    @PostMapping(path = "/hotel")
    public String editHotel(Model model, HotelDto hotelDto) {
        if (hotelDto != null) {
            Hotel hotel = hotelService.getHotel();
            hotel.setDescription(hotelDto.getDescription());
            hotel.setAddress(hotelDto.getAddress());
            hotel.setPhoneNumber(hotelDto.getPhoneNumber());
            hotel.setEmail(hotelDto.getEmail());
            hotelService.saveHotel(hotel);
        }
        return getMainPage(model);
    }

    @PostMapping(path = "/room{number}")
    public String editRoom(Model model, RoomDto roomDto, @PathVariable Integer number) {
        if (roomDto != null && roomDto.getDiscount() >= 0 && roomDto.getCost() > roomDto.getDiscount()
                && roomDto.getSingleBedsCount() >= 0 && roomDto.getDoubleBedsCount() >= 0
                && roomDto.getAdultCount() > 0 && roomDto.getChildCount() >= 0) {
            Room room = roomService.findRoomByNumber(number);
            room.setDescription(roomDto.getDescription());
            room.setCost(roomDto.getCost());
            room.setDiscount(roomDto.getDiscount());
            room.setSingleBedsCount(roomDto.getSingleBedsCount());
            room.setDoubleBedsCount(roomDto.getDoubleBedsCount());
            room.setAdultCount(roomDto.getAdultCount());
            room.setChildCount(roomDto.getChildCount());
            roomService.saveRoom(room);
        }
        return getMainPage(model);
    }

    @PostMapping(path = "/amenities")
    public String editAmenities(Model model, AmenitiesDto amenitiesDto) {
        if (amenitiesDto != null) {
            List<Amenities> allAmenities = amenitiesService.findAll();
            Map<Integer, Room> allRooms = roomService.getNumberToRoom();
            amenitiesDto.getAmenitiesRows().forEach(amenitiesRow -> {
                Amenities amenities = findAmenitiesById(allAmenities, amenitiesRow.getId());
                if (amenities == null) {
                    amenities = amenitiesRow.toAmenities();
                }
                List<Room> rooms = new ArrayList<>();
                if (amenitiesRow.isInRoom1()) {
                    rooms.add(allRooms.get(1));
                }
                if (amenitiesRow.isInRoom2()) {
                    rooms.add(allRooms.get(2));
                }
                if (amenitiesRow.isInRoom3()) {
                    rooms.add(allRooms.get(3));
                }
                if (amenitiesRow.isInRoom4()) {
                    rooms.add(allRooms.get(4));
                }
                amenities.setRooms(rooms);
                amenitiesService.saveAmenities(amenities);
            });
        }
        return getMainPage(model);
    }

    private Amenities findAmenitiesById(List<Amenities> amenities, long id) {
        for (Amenities a : amenities) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    @PostMapping(path = "/amenities/add")
    public String addAmenities(Model model, AmenitiesDto.AmenitiesRow amenitiesRow) {
        if (amenitiesRow != null) {
            Amenities amenities = amenitiesService.findByName(amenitiesRow.getName());
            if (amenities == null) {
                amenities = amenitiesRow.toAmenities();
                Map<Integer, Room> allRooms = roomService.getNumberToRoom();
                List<Room> rooms = new ArrayList<>();
                if (amenitiesRow.isInRoom1()) {
                    rooms.add(allRooms.get(1));
                }
                if (amenitiesRow.isInRoom2()) {
                    rooms.add(allRooms.get(2));
                }
                if (amenitiesRow.isInRoom3()) {
                    rooms.add(allRooms.get(3));
                }
                if (amenitiesRow.isInRoom4()) {
                    rooms.add(allRooms.get(4));
                }
                amenities.setRooms(rooms);
                amenitiesService.saveAmenities(amenities);
            }
        }
        return getMainPage(model);
    }

    @PostMapping(path = "/amenities/delete/" + "{id}")
    public String deleteAmenities(Model model, @PathVariable Long id) {
        Amenities amenities = amenitiesService.findById(id);
        if (amenities != null) {
            amenitiesService.deleteAmenities(amenities);
        }
        return getMainPage(model);
    }
}
