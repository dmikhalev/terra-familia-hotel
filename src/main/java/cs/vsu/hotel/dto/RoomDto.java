package cs.vsu.hotel.dto;

import cs.vsu.hotel.entity.Amenities;
import cs.vsu.hotel.entity.Room;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class RoomDto {

    private Long id;
    private Integer number;
    private Integer cost;
    private Integer discount;
    private Integer adultCount;
    private Integer childCount;
    private String description;
    private Integer doubleBedsCount;
    private Integer singleBedsCount;

    private List<Amenities> amenities;

    private Integer price;

    public RoomDto() {
    }

    public RoomDto(Long id, Integer number, Integer cost, Integer discount, Integer adultCount, Integer childCount,
                   String description, Integer doubleBedsCount, Integer singleBedsCount, List<Amenities> amenities) {
        this.id = id;
        this.number = number;
        this.cost = cost;
        this.discount = discount;
        this.adultCount = adultCount;
        this.childCount = childCount;
        this.description = description;
        this.doubleBedsCount = doubleBedsCount;
        this.singleBedsCount = singleBedsCount;
        this.amenities = amenities;
    }

    public Room toRoom() {
        Room room = new Room(id, number, cost, discount, adultCount, childCount, description, doubleBedsCount, singleBedsCount);
        room.setAmenities(amenities);
        return room;
    }

    public static RoomDto fromRoom(Room room) {
        return new RoomDto(
                room.getId(),
                room.getNumber(),
                room.getCost(),
                room.getDiscount(),
                room.getAdultCount(),
                room.getChildCount(),
                room.getDescription(),
                room.getDoubleBedsCount(),
                room.getSingleBedsCount(),
                room.getAmenities());
    }

    public static List<RoomDto> fromRooms(List<Room> rooms) {
        return rooms.stream().map(RoomDto::fromRoom).collect(Collectors.toList());
    }
}
