package formLogin.form.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public String mainAPI() {
        return "main controller";
    }

    @GetMapping("/loginSuccess")
    public String successAPI() {
        return "login success";
    }
}
