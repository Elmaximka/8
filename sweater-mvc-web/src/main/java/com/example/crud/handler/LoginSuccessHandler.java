package com.example.crud.handler;

import com.example.crud.model.Role;
import com.example.crud.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException {
        User user = (User) authentication.getPrincipal();
        if (user.getAuthorities().contains(new Role("admin"))) {
            response.sendRedirect("/admin");
        } else if (user.getAuthorities().contains(new Role("user"))) {
            response.sendRedirect("/user");
        }

    }
}
