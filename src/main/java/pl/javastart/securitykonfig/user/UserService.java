package pl.javastart.securitykonfig.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder; //pobieramy password encoder do kodowania hasel
    private final UserRepository userRepository; //umozliwia dostep do bazy danych

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void registerUser(String username, String rawPassword) {
        User userToAdd = new User(); // tworzymy encje typu user i uzupełniamy ją danymi

        userToAdd.setUsername(username);
        String encryptedPassword = passwordEncoder.encode(rawPassword); // hasła nie przekazujemy prawdziwego tylko je szyfrujemy
        userToAdd.setPassword(encryptedPassword);

        //lista ról
        List<UserRole> list = Collections.singletonList(new UserRole(userToAdd, Role.ROLE_USER));
//        hasgset z listy
        userToAdd.setRoles(new HashSet<>(list));

        userRepository.save(userToAdd);
    }
}
