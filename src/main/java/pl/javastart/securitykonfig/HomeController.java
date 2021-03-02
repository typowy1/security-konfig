package pl.javastart.securitykonfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

    @GetMapping("")
    public String homepage() {
        return "home";
    }

    @GetMapping("/secure")
    public String secure() {
        return "secure";
    }
}
