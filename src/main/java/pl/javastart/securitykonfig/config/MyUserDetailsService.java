package pl.javastart.securitykonfig.config;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.javastart.securitykonfig.user.User;
import pl.javastart.securitykonfig.user.UserRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional //dodajemy sesje bo wyjatek wyskakuje ze nie moze polaczyc sie z sesja
    @Override //załaduj użytkownika za pomocą jego nazwy, weryfikacja przesłanych danych przez użytkownika
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //szukamy uzytkownika
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Set<SimpleGrantedAuthority> roles = user.getRoles()
                    .stream()
                    .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().name())).collect(Collectors.toSet());
            // przekształcam user role na SimpleGrantedAuthority i wyciągam nazwe roli i wstawiam do seta

            Collection<SimpleGrantedAuthority> role; // to sprawdzamy role
            // to nie jest nasz user a user z org.springframework.security.core.userdetails.User;
           // return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles);
            //wskazaliśmy pełną ściezkę żeby się Usery nie myliły
        }
        throw new UsernameNotFoundException("Username" + email + "not found");
    }
}
