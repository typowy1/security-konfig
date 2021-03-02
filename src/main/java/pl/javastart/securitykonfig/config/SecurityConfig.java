package pl.javastart.securitykonfig.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // clasa do konfiguracji security


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/img/**").permitAll() //gwiazdki oznaczają ze wszystkie z foldru
//                .antMatchers("/").permitAll() //strona będzie widoczna dla wszystkich
//                //.antMatchers("/secure").authenticated() //strona będzie widoczna tylko dla zalogowanych
//                .anyRequest().permitAll()  // jeśli przyjdzie żadanie to pozwalamy każdemu na przejscie do tego elementu
//                .and()  // przechodzimy poziom wyżej
//                .formLogin() // logujemy za pomocą formularza
//                .loginPage("logowanie") // strona odpowiedzialna za logowania
//                .permitAll();

        http.authorizeRequests()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/h2-console/**").permitAll() //dostep dla wszystkich
                .antMatchers("/rejestracja").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/logowanie")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/"); //do wylogowania
    }

    @Override
    public void configure(WebSecurity web) throws Exception { // seciurity dla bazy danej
        web.ignoring()
                .antMatchers("/h2-console/**"); // ignorujemy security dla consoli h2
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //do odkodowania hasła w bazie
    }
}
