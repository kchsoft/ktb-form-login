package formLogin.form.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationTestController {

    @GetMapping("/test")
    public String test(){
        return "authentication success";
    }

}
