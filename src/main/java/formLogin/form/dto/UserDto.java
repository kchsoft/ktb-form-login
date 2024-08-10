package formLogin.form.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDto {
    private String koreanName;
    private String englishName;
    private String course;
    private String email;
    private String role;
    private String name;

    @Override
    public String toString() {
        return "UserDto{" +
                "koreanName='" + koreanName + '\'' +
                ", englishName='" + englishName + '\'' +
                ", course='" + course + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
