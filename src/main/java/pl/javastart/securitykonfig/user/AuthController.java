package pl.javastart.securitykonfig.user;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
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

    //rejestracja z formularza

    @PostMapping("/rejestracja")
    public String register(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        userService.registerUser(username, password);
        return "redirect:/"; //powinno być na strone z komunikatem, udało sie zarejestrowac
    }

//    @PostMapping("/logowanie")
//    public String loginForm() {
//        return "login";
//    }

}
