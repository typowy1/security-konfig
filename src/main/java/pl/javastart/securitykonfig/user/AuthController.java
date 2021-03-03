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
//        String username = user.getUsername();
        String email = user.getEmail();
        String password = user.getPassword();
        userService.registerUser(email, password);
        return "redirect:/"; //powinno być na strone z komunikatem, udało sie zarejestrowac
    }

    //zwracamy formularz do resetu hasła
    @GetMapping("/reset")
    public String resetForm() {
        return "resetForm";
    }

    //przechwycimy email i temu użytkownikowi jak istnieje wyslemy maila do resetu hasla
    @PostMapping("/reset")
    public String resetPasswordLinkSend(@RequestParam String email) {
        userService.sendPasswordResetLink(email);
        return "resetFormSend";
    }

    //strona która otwiera sie po kliknieciu na link w mailu, przechwytujemy klucz, formularz z resetowaniem hasła
    @GetMapping("/resetHasla")
    public String resetPassword(@RequestParam("klucz") String key, Model model) {
        model.addAttribute("key", key);
        return "resetFormWithKey";
    }

    //przechwytujemy z formularza dane o zmianie hasła
    @PostMapping("/resetEnding")
    public String resetPasswordLinkSend(@RequestParam String key, @RequestParam String password) {
        userService.updateUserPassword(key, password); //podajemy klucz uzytkownika i nowe chasło
        return "redirect:/logowanie"; //mozna tez przekierowac do strony z monitem jakims
    }
}
