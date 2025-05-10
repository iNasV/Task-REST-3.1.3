package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
    private final DbUserDetailService dbUserDetailService;

    public CustomUserDetailService(InMemoryUserDetailsManager inMemoryUserDetailsManager, DbUserDetailService dbUserDetailService) {
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
        this.dbUserDetailService = dbUserDetailService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        try {
            return inMemoryUserDetailsManager.loadUserByUsername(login);
        } catch (UsernameNotFoundException e) {
            return dbUserDetailService.loadUserByUsername(login);
        }
    }
}
