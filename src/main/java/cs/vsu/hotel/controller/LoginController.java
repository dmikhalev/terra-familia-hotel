package cs.vsu.hotel.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping()
    public String createLoginForm() {
        return "login";
    }

    @PostMapping()
    public String login(Authentication authentication, Model model) {
        if(authentication !=null){
            System.out.println("hjuhjjh");
        }
        return "/index";
    }
}
