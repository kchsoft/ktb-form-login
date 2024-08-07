package formLogin.form.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("/my")
    public String myAPI() {
        return "my route";
    }

    @GetMapping("/my2")
    public String my2API() {
        return "my2 route";
    }

}
