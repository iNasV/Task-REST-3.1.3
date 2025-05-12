package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itmentor.spring.boot_security.demo.model.Person;
import ru.itmentor.spring.boot_security.demo.security.PersonDetails;

import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        try {
            var user = (User) authentication.getPrincipal();
            httpServletResponse.sendRedirect("/");
            return;
        } catch (ClassCastException e) {   }

        var personDetails = (PersonDetails) authentication.getPrincipal();
        var id = personDetails.getPerson().getId();

        if (roles.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/admin/"+id);
        } else if (roles.contains("ROLE_USER")) {
            httpServletResponse.sendRedirect("/user/"+id);
        } else {
            httpServletResponse.sendRedirect("/");
        }
    }
}