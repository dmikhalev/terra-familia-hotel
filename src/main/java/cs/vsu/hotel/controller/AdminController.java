package cs.vsu.hotel.controller;

import cs.vsu.hotel.dto.BookingDto;
import cs.vsu.hotel.dto.ClientDto;
import cs.vsu.hotel.entity.Booking;
import cs.vsu.hotel.entity.Client;
import cs.vsu.hotel.entity.Payment;
import cs.vsu.hotel.service.BookingService;
import cs.vsu.hotel.service.ClientService;
import cs.vsu.hotel.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BookingService bookingService;
    private final PaymentService paymentService;
    private final ClientService clientService;
    private Long passportId;
    private boolean showAll;

    public AdminController(BookingService bookingService, PaymentService paymentService, ClientService clientService) {
        this.bookingService = bookingService;
        this.paymentService = paymentService;
        this.clientService = clientService;
    }

    @GetMapping()
    public String createMenu() {
        return "admin";
    }

    @GetMapping(path = "/booking")
    public String createBooking() {
        return "admin/booking";
    }

    @PostMapping(path = "/booking")
    public String getUnpaidBookingsByPassportId(Model model, String passportId) {
        showAll = false;
        model.addAttribute("bookingDtos", getBookingDtosByPassportId(passportId, false));
        return "admin/booking";
    }

    @PostMapping(path = "/booking/all")
    public String getAllUnpaidBookings(Model model) {
        showAll = true;
        model.addAttribute("bookingDtos", getAllBookingDtos(false));
        return "admin/booking";
    }

    @PostMapping(path = "booking/pay/" + "{id}")
    public String pay(Model model, @PathVariable Long id) {
        Booking booking = bookingService.findById(id);
        if (booking != null) {
            Payment payment = new Payment(booking.getPrice());
            paymentService.savePayment(payment);
            booking.setPayment(payment);
            bookingService.saveBooking(booking);
        }
        return showAll ? getUnpaidBookingsByPassportId(model, String.valueOf(passportId)) : getAllUnpaidBookings(model);
    }

    @PostMapping(path = "booking/delete/" + "{id}")
    public String deleteBooking(Model model, @PathVariable Long id) {
        Booking booking = bookingService.findById(id);
        if (booking != null) {
            bookingService.deleteBooking(booking);
        }
        return showAll ? getUnpaidBookingsByPassportId(model, String.valueOf(passportId)) : getAllUnpaidBookings(model);
    }


    @GetMapping(path = "/payment")
    public String createPayment() {
        return "admin/payment";
    }

    @PostMapping(path = "/payment")
    public String getPaidBookingsByPassportId(Model model, String passportId) {
        showAll = false;
        model.addAttribute("bookingDtos", getBookingDtosByPassportId(passportId, true));
        return "admin/payment";
    }

    @PostMapping(path = "/payment/all")
    public String getAllPaidBookings(Model model) {
        showAll = true;
        model.addAttribute("bookingDtos", getAllBookingDtos(true));
        return "admin/payment";
    }

    @PostMapping(path = "payment/cancel/" + "{id}")
    public String cancelPayment(Model model, @PathVariable Long id) {
        Booking booking = bookingService.findById(id);
        if (booking != null && booking.getPayment() != null) {
            Payment payment = booking.getPayment();
            booking.setPayment(null);
            bookingService.saveBooking(booking);
            paymentService.delete(payment);
        }
        return showAll ? getPaidBookingsByPassportId(model, String.valueOf(passportId)) : getAllUnpaidBookings(model);
    }

    private List<BookingDto> getAllBookingDtos(boolean isPaid) {
        List<Booking> bookings = isPaid ? bookingService.getAllPaidBookings() : bookingService.getAllUnpaidBookings();
        List<BookingDto> bookingDtos = new ArrayList<>();
        if (bookings != null && !bookings.isEmpty()) {
            bookings.forEach(b -> bookingDtos.add(BookingDto.toBookingDto(b)));
        }
        return bookingDtos;
    }

    private List<BookingDto> getBookingDtosByPassportId(String passportId, boolean isPaid) {
        long passportIdL;
        try {
            passportIdL = Long.parseLong(passportId.replace(" ", ""));
        } catch (NumberFormatException e) {
            return Collections.emptyList();
        }
        this.passportId = passportIdL;

        List<Booking> bookings;
        if (isPaid) {
            bookings = bookingService.getPaidBookingsByPassportId(passportIdL);
        } else {
            bookings = bookingService.getUnpaidBookingsByPassportId(passportIdL);
        }

        if (bookings != null && !bookings.isEmpty()) {
            return bookings.stream()
                    .map(BookingDto::toBookingDto)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @GetMapping(path = "/clients")
    public String createClientsMenu(Model model) {
        showAll = true;
        List<Client> clients = new ArrayList<>(clientService.findAll());
        List<ClientDto> clientDtos = clients.stream()
                .map(ClientDto::toClientDto)
                .sorted(Comparator.comparing(ClientDto::getName))
                .collect(Collectors.toList());

        model.addAttribute("clients", clientDtos);
        return "admin/clients";
    }

    @PostMapping(path = "clients/delete/" + "{id}")
    public String deleteClient(Model model, @PathVariable Long id) {
        Client client = clientService.findById(id);
        if (client != null) {
            List<Booking> bookings = client.getBookings();
            if (bookings != null) {
                bookings.forEach(bookingService::deleteBooking);
            }
            clientService.deleteClient(client);
        }
        return createClientsMenu(model);
    }

    @PostMapping(path = "clients/")
    public String getClientsByName(Model model, ClientDto clientDto) {
        showAll = false;
        passportId = clientDto.getPassportIdLong();
        if (passportId != null) {
            Client client = clientService.findByPassportId(passportId);
            if (client != null) {
                List<ClientDto> clientDtos = Collections.singletonList(ClientDto.toClientDto(client));
                model.addAttribute("clients", clientDtos);
                return "admin/clients";
            }
        }

        String name = clientDto.getName();
        if (name == null) {
            return "admin/clients";
        }

        List<Client> clients = clientService.findByPartOfName(name);
        List<ClientDto> clientDtos = clients.stream()
                .map(ClientDto::toClientDto)
                .sorted(Comparator.comparing(ClientDto::getName))
                .collect(Collectors.toList());

        model.addAttribute("clients", clientDtos);
        return "admin/clients";
    }
}
