package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.itmentor.spring.boot_security.demo.service.CustomUserDetailService;
import ru.itmentor.spring.boot_security.demo.service.DbUserDetailService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final SuccessUserHandler successUserHandler;

    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.authenticationProvider(daoAuthenticationProvider);

        http
                .authorizeHttpRequests(
                auth -> auth
                        .requestMatchers("/","/index").permitAll()
                        .requestMatchers("/API/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers("/API/**"))
                .formLogin(
                        login -> login
                                .successHandler(successUserHandler)
                                .permitAll())
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(InMemoryUserDetailsManager inMemoryUserDetailsManager, DbUserDetailService dbUserDetailService) {
        return new CustomUserDetailService(inMemoryUserDetailsManager, dbUserDetailService);
    }


    // аутентификация inMemory
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {

        UserDetails admin =
                User.withUsername("admin")
                        .password(passwordEncoder()
                                .encode("admin"))
                        .roles("ADMIN","USER")
                        .build();

        UserDetails user =
                User.withUsername("user")
                        .password(passwordEncoder()
                                .encode("user"))
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}