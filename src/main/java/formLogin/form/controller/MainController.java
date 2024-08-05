package formLogin.form.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

@RestController
public class MainController {

    @GetMapping("/")
    public String mainPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Collection<? extends GrantedAuthority> collection = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = collection.iterator();
        GrantedAuthority grandtedAuth = iterator.next();
        String role = grandtedAuth.getAuthority();


        return "Main Controller : " + username + "  " + role;
    }

}
