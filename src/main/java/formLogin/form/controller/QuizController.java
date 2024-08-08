package formLogin.form.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;

@RestController
public class QuizController {

    @GetMapping("/checkAnswer")
    public ResponseEntity<Boolean> checkAnswer(String quizAnswer) throws IOException {
        System.out.println("quizAnswer = " + quizAnswer);
        if (quizAnswer.equals("a")) {
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.ok(false);
    }
}
