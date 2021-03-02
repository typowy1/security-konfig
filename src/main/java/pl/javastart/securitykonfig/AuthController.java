package pl.javastart.securitykonfig;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    // kontroler odpowiedzialny za logowanie

    @GetMapping("/logowanie")
    public String loginForm() {
        return "login";
    }

//    @PostMapping("/logowanie")
//    public String loginForm() {
//        return "login";
//    }

}
