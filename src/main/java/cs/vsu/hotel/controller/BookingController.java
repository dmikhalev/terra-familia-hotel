package cs.vsu.hotel.controller;

import cs.vsu.hotel.dto.BookingDto;
import cs.vsu.hotel.dto.FindRoomDto;
import cs.vsu.hotel.entity.Booking;
import cs.vsu.hotel.entity.Client;
import cs.vsu.hotel.entity.Room;
import cs.vsu.hotel.service.BookingService;
import cs.vsu.hotel.service.ClientService;
import cs.vsu.hotel.service.RoomService;
import cs.vsu.hotel.util.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private final RoomService roomService;
    private final BookingService bookingService;
    private final ClientService clientService;
    private Date startDate;
    private Date endDate;

    public BookingController(RoomService roomService, BookingService bookingService, ClientService clientService) {
        this.roomService = roomService;
        this.bookingService = bookingService;
        this.clientService = clientService;
    }

    @GetMapping()
    public String createBookingForm() {
        return "redirect:find-room";
    }

    @GetMapping(path = "/{number}")
    public String createBookingForm1(Model model, @PathVariable Integer number) {
        Room room = roomService.findRoomByNumber(number);
        if (room == null) {
            return "redirect:find-room";
        }
        model.addAttribute("room", room);
        model.addAttribute("roomImgNames", getImgNames(number));
        return "booking";
    }

    @PostMapping(path = "/{number}")
    public String createClient(Model model, FindRoomDto findRoomDto, @PathVariable Integer number) {
        Room room = roomService.findRoomByNumber(number);
        if (room == null) {
            return "redirect:find-room";
        }
        startDate = findRoomDto.getFormatStartDate();
        endDate = findRoomDto.getFormatEndDate();
        model.addAttribute("roomImgNames", getImgNames(number));
        model.addAttribute("startDate", DateUtils.formatDate(findRoomDto.getStartDate()));
        model.addAttribute("endDate", DateUtils.formatDate(findRoomDto.getEndDate()));


        model.addAttribute("room", room);
        model.addAttribute("price", roomService.calcPrice(room, findRoomDto.getFormatStartDate(), findRoomDto.getFormatEndDate()));
        return "booking";
    }

    @PostMapping(path = "success/" + "{number}")
    public String book(Model model, BookingDto bookingDto, @PathVariable Integer number) {
        if (bookingDto.getPassportIdL() == null) {
            return "booking/" + number;
        }
        Client existedClient = clientService.findByPassportId(bookingDto.getPassportIdL());
        Client client = new Client(bookingDto.getFirstName(), bookingDto.getLastName(), bookingDto.getPassportIdL(),
                bookingDto.getAge(), bookingDto.getGender(), bookingDto.getPhoneNumber(), bookingDto.getEmail());
        if (existedClient != null) {
            client.setId(existedClient.getId());
        }
        client = clientService.saveClient(client);

        Room room = roomService.findRoomByNumber(number);
        if (room == null) {
            return "redirect:find-room";
        }

        int price = roomService.calcPrice(room, startDate, endDate);
        Booking booking = new Booking(roomService.findRoomByNumber(number), client, null,
                startDate, endDate, price);
        bookingService.saveBooking(booking);

        bookingDto.setRoomNumber(number);
        bookingDto.setStartDate(DateUtils.dateToDdMmYyyyStr(startDate));
        bookingDto.setEndDate(DateUtils.dateToDdMmYyyyStr(endDate));
        model.addAttribute("booking", bookingDto);
        model.addAttribute("booked", true);
        return "success-booking";
    }


    private List<String> getImgNames(Integer roomNumber) {
        File folder = new File("src/main/resources/static/img/rooms/room" + roomNumber);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null || listOfFiles.length == 0) {
            return Collections.emptyList();
        }
        final String prefix = "/img/rooms/room" + roomNumber + "/";
        return Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .map(f -> prefix + f.getName())
                .collect(Collectors.toList());
    }
}
