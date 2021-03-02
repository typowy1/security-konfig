package pl.javastart.securitykonfig.config;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.javastart.securitykonfig.user.User;
import pl.javastart.securitykonfig.user.UserRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override //załaduj użytkownika za pomocą jego nazwy, weryfikacja przesłanych danych przez użytkownika
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //szukamy uzytkownika
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.getRoles()
                    .stream()
                    .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().name()));
            // przekształcam user role na SimpleGrantedAuthority i wyciągam nazwe roli

            Collection<SimpleGrantedAuthority> role; // to sprawdzamy role
            // to nie jest nasz user a user z org.springframework.security.core.userdetails.User;
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), role);
            //wskazaliśmy pełną ściezkę żeby się Usery nie myliły
        }
        return null;
    }
}
