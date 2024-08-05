package formLogin.form.controller;

import formLogin.form.dto.JoinDto;
import formLogin.form.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {


    private final JoinService joinService;


    @PostMapping("/join")
    public String joinPage(JoinDto dto){
        String response = joinService.joinProcess(dto);
        return response;
    }

}
