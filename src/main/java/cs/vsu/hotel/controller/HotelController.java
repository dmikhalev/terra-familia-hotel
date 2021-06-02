package cs.vsu.hotel.controller;

import cs.vsu.hotel.entity.Hotel;
import cs.vsu.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping()
    public String createIndex(Model model) {
        Hotel hotel = hotelService.getHotel();
        if (hotel != null) {
            model.addAttribute("description", hotel.getDescription());
            model.addAttribute("phoneNumber", hotel.getPhoneNumber());
            model.addAttribute("email", hotel.getEmail());
            model.addAttribute("address", hotel.getAddress());
        }
        return "index";
    }

    @PostMapping()
    public String createPostIndex(Model model) {
        return createIndex(model);
    }
}
