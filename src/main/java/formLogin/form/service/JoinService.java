package formLogin.form.service;


import formLogin.form.dto.JoinDto;
import formLogin.form.entity.UserEntity;
import formLogin.form.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;

    private final PasswordEncoder bCryptPasswordEncoder;

    public String joinProcess(JoinDto dto){

        if (userRepository.existsByUsername(dto.getUsername())){
            return "fail : user exist";
        }
        UserEntity user = new UserEntity();
        user.setUsername(dto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        user.setRole("ADMIN");
        userRepository.save(user);
        return "success : join complete";
    }

}


