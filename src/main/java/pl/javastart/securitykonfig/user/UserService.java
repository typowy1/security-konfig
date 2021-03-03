package pl.javastart.securitykonfig.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.javastart.securitykonfig.mail.MailSenderService;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;

    public UserService(PasswordEncoder passwordEncoder,
                       UserRepository userRepository, MailSenderService mailSenderService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.mailSenderService = mailSenderService;
    }

    public void registerUser(String email, String rawPassword) {
        User userToAdd = new User(); // tworzymy encje typu user i uzupełniamy ją danymi

//        userToAdd.setUsername(username);
        userToAdd.setEmail(email);
        String encryptedPassword = passwordEncoder.encode(rawPassword); // hasła nie przekazujemy prawdziwego tylko je szyfrujemy
        userToAdd.setPassword(encryptedPassword);

        //lista ról
        List<UserRole> list = Collections.singletonList(new UserRole(userToAdd, Role.ROLE_USER));
//        hasgset z listy
        userToAdd.setRoles(new HashSet<>(list));

        userRepository.save(userToAdd);
    }

    //wyswietl użytkownikow procz zalogowanego
    public List<User> findAllWithoutCurrentUser() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication(); // sciaga informacje o zalogowanym userze

        return userRepository.findAll()
                .stream()
//                .filter(user -> !user.getUsername().equals(currentUser.getName())) //username musi byc rozne od aktualnego usera
                .filter(user -> !user.getEmail().equals(currentUser.getName())) //email musi byc rozne od aktualnego usera
                .collect(Collectors.toList());
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public void sendPasswordResetLink(String email) {
        //pierw szukamy użytkownika o danym mailu
        userRepository.findByEmail(email).ifPresent(user -> { //to wyrazenie lambda wykona sie wtedy kiedy user bedzie istnial
            String key = UUID.randomUUID().toString(); // randomowy klucz musimy wygenerowac unikalny ciag znakow, UUID - unikalne kluicze
            user.setPasswordResetKey(key); //ustawiamy klucz
            userRepository.save(user); //zapisujemy klucz użytkownikowi

            try {
                mailSenderService.sendPasswordResetLink(email, key); //wysyłamy mail i lokalizujemy po kluczu
            } catch (Exception e) { //przechwytujemy wyjatek
                e.printStackTrace();
            }

        });

    }

    public void updateUserPassword(String key, String password) {
        userRepository.findByPasswordResetKey(key).ifPresent(  //szukamy uzytkownika po unikalnym kluczu
                user -> {
                    user.setPassword(passwordEncoder.encode(password)); //ustawia zaszyfrowane hasło, jezeli jest taki user to lambda zmieni mu hasło przesyłamy przez enkodera, w bazie będzie hasło zaszyfrowane bcryptem nie noop
                    user.setPasswordResetKey(null); //ustawiamy reset password key na null, został juz wykożystany
                    userRepository.save(user);
                }
        );
    }
}
