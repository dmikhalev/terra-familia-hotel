package cs.vsu.hotel.service;

import cs.vsu.hotel.entity.Room;
import cs.vsu.hotel.repository.RoomRepository;
import cs.vsu.hotel.util.DateUtils;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findUnbookedRooms(Date startDate, Date endDate, int adultCount, int childCount) {
        return roomRepository.findUnbookedRooms(startDate, endDate, adultCount, childCount);
    }

    public void sortRoomsByNumber(List<Room> rooms) {
        rooms.sort(Comparator.comparing(Room::getCost));
    }

    public int calcCost(Room room) {
        return room.getCost() - room.getDiscount();
    }

    public int calcPrice(Room room, Date startDate, Date endDate) {
        int cost = calcCost(room);
        return cost * (DateUtils.getDaysBetween(startDate, endDate));
    }

    public Room findById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    public Room findRoomByNumber(Integer number) {
        return roomRepository.getRoomByNumber(number);
    }


    public List<Room> findAll() {
        return new ArrayList<>(roomRepository.findAll());
    }

    public Map<Integer, Room> getNumberToRoom() {
        Map<Integer, Room> result = new HashMap<>();
        List<Room> rooms = findAll();
        rooms.forEach(r -> result.put(r.getNumber(), r));
        return result;
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
