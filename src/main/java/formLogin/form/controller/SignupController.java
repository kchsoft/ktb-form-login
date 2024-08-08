    package formLogin.form.controller;

    import formLogin.form.dto.UserDto;
    import jakarta.servlet.http.HttpServletResponse;
    import org.springframework.web.bind.annotation.*;

    import java.io.IOException;
    import java.util.HashMap;
    import java.util.Map;

    @RestController
    public class SignupController {

        @PostMapping("/signup")
        public Map<String,String> signup(@RequestBody UserDto userDto) throws IOException {
            System.out.println("userDto = " + userDto);
            Map<String, String> response = new HashMap<>();
            response.put("redirect", "http://localhost:3000/loginQuiz");
            response.put("message", "login success");

            return response;
        }
    }
