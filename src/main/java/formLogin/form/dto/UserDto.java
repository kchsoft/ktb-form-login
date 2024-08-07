package formLogin.form.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDto {

    private String name;
    private String username;
    private String role;
}
