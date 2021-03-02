package pl.javastart.securitykonfig.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.javastart.securitykonfig.user.User;
import pl.javastart.securitykonfig.user.UserService;

import java.util.List;

@RequestMapping("/admin")
@Controller
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    //panel administratora administrator powinien mieć możliwość usuwania użytkowników
    @GetMapping("")
    public String adminPanel(Model model) {
        List<User> users = userService.findAllWithoutCurrentUser();
        model.addAttribute("users", users);
// dodajemy model i do modelu wszystkich uzytkownikow
        return "admin";
    }

    //usowanie usera po id
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
