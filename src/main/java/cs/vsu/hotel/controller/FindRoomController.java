package cs.vsu.hotel.controller;

import cs.vsu.hotel.dto.FindRoomDto;
import cs.vsu.hotel.dto.RoomDto;
import cs.vsu.hotel.entity.Room;
import cs.vsu.hotel.service.RoomService;
import cs.vsu.hotel.util.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.List;

@Controller
public class FindRoomController {

    private final RoomService roomService;

    public FindRoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/find-room")
    public String createFindRoomForm(Model model) {
        List<Room> rooms = roomService.findAll();
        roomService.sortRoomsByNumber(rooms);
        model.addAttribute("rooms", rooms);
        return "find-room";
    }

    @PostMapping("/find-room")
    public String findFreeRooms(Model model, FindRoomDto findRoomDto) {
        // all rooms
        if (findRoomDto.getFormatStartDate() == null || findRoomDto.getFormatEndDate() == null) {
            model.addAttribute("rooms", roomService.findAll());
            model.addAttribute("showPrice", false);
            return "/find-room";
        }
        // error
        if (findRoomDto.getFormatStartDate().getTime() < System.currentTimeMillis()) {
            model.addAttribute("errorMessage", "Некорректные даты: дата заезда должна быть не ранее чем текущая дата.");
            model.addAttribute("rooms", Collections.emptyList());
            model.addAttribute("showPrice", false);
            return "/find-room";
        }
        // error
        if (findRoomDto.getFormatStartDate().getTime() > findRoomDto.getFormatEndDate().getTime()) {
            model.addAttribute("errorMessage", "Некорректные даты: дата заезда должна быть не ранее чем дата выезда.");
            model.addAttribute("rooms", Collections.emptyList());
            model.addAttribute("showPrice", false);
            return "/find-room";
        }
        //
        List<Room> freeRooms = roomService.findUnbookedRooms(
                findRoomDto.getFormatStartDate(), findRoomDto.getFormatEndDate(),
                findRoomDto.getAdultCount(), findRoomDto.getChildCount());
        roomService.sortRoomsByNumber(freeRooms);

        List<RoomDto> roomDtos = RoomDto.fromRooms(freeRooms);
        initPrices(roomDtos, findRoomDto);

        model.addAttribute("errorMessage", "Нет доступных комнат");
        model.addAttribute("rooms", roomDtos);
        model.addAttribute("startDate", findRoomDto.getStartDate());
        model.addAttribute("endDate", findRoomDto.getEndDate());
        model.addAttribute("adultCount", findRoomDto.getAdultCount());
        model.addAttribute("childCount", findRoomDto.getChildCount());
        model.addAttribute("showPrice", true);
        return "/find-room";
    }

    private void initPrices(List<RoomDto> roomDtos, FindRoomDto findRoomDto) {
        int days = DateUtils.getDaysBetween(DateUtils.parseDate(findRoomDto.getEndDate()),
                DateUtils.parseDate(findRoomDto.getStartDate()));
        roomDtos.forEach(r -> {
            r.setPrice(days * (r.getCost() - r.getDiscount()));
        });
    }
}
