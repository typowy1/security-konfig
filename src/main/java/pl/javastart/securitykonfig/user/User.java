package pl.javastart.securitykonfig.user;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}) //{CascadeType.PERSIST, CascadeType.REMOVE} to jest tablica i mozna dodac kilka remove da mozliwosc usowania mimo roli w innej tabeli, //cascade = CascadeType.PERSIST - jesli nadamy role i damy zapisz to zapisze te role razem z nimi
    private Set<UserRole> roles;  // role do zarządzania użytkownikami, najczęsciej robi się relacje jeden do wielu

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
}
