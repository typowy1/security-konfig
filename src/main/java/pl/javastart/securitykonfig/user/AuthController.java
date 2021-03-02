package pl.javastart.securitykonfig.user;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    // kontroler odpowiedzialny za logowanie

    @GetMapping("/logowanie")
    public String loginForm(@RequestParam(required = false) String error,
                            Model model) {
        boolean showErrorMessage = false;

        if (error != null) {
            showErrorMessage = true;
        }
        model.addAttribute("showErrorMessage", showErrorMessage);

        return "login";
    }

//    @PostMapping("/logowanie")
//    public String loginForm() {
//        return "login";
//    }

}
