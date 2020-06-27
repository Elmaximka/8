package com.example.crud.handler;

import com.example.crud.model.Role;
import com.example.crud.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException {
        User user = (User) authentication.getPrincipal();
        Collection<Role> gr = (Collection<Role>) user.getAuthorities();
        boolean role = gr.contains(new Role("ADMIN"));
        if (user.getAuthorities().contains(new Role("ADMIN"))) {
            response.sendRedirect("/admin");
        } else if (user.getAuthorities().contains(new Role("USER"))) {
            response.sendRedirect("/user");
        }
        else response.sendRedirect("/login");

    }
}
