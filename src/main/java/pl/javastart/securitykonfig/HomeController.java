package pl.javastart.securitykonfig;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.javastart.securitykonfig.user.User;

@Controller
public class HomeController {

    @GetMapping("")
    public String homepage(Model model) {

        //dostarczam usera do modelu i przekazuje do formularza
        model.addAttribute("user", new User());
        return "home";
    }

    @GetMapping("/secure")
    public String secure() {
        return "secure";
    }
}
