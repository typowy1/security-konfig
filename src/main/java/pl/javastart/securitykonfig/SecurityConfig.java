package pl.javastart.securitykonfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.hibernate.criterion.Restrictions.and;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // clasa do konfiguracji security


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/img/**").permitAll() //gwiazdki oznaczają ze wszystkie z foldru
                .antMatchers("/").permitAll() //strona będzie widoczna dla wszystkich
                //.antMatchers("/secure").authenticated() //strona będzie widoczna tylko dla zalogowanych
                .anyRequest().permitAll()  // jeśli przyjdzie żadanie to pozwalamy każdemu na przejscie do tego elementu
                .and()  // przechodzimy poziom wyżej
                .formLogin() // logujemy za pomocą formularza
                .loginPage("logowanie") // strona odpowiedzialna za logowania
                .permitAll();




    }
}
