package formLogin.form.service;

import formLogin.form.dto.CustomOAuth2User;
import formLogin.form.dto.KakaoResponse;
import formLogin.form.dto.OAuth2Response;
import formLogin.form.dto.UserDto;
import formLogin.form.entity.UserEntity;
import formLogin.form.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("here is oauth2 service");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        switch (registrationId) {
            case "naver" :
                break;
            case "google" :
                break;
            case "kakao" :
                oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
                break;
            default:
                break;
        }

        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

        UserEntity existData = userRepository.findByUsername(username);

        if (existData == null) {

            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setName(oAuth2Response.getName());
            userEntity.setRole("USER");
            userRepository.save(userEntity);

            UserDto userDto = new UserDto();
            userDto.setName(oAuth2Response.getName());
            userDto.setRole("USER");

            return new CustomOAuth2User(userDto);
        } else {
            existData.setUsername(username);
            existData.setName(oAuth2Response.getName());

            userRepository.save(existData);

            UserDto userDto = new UserDto();
            userDto.setName(oAuth2Response.getName());
            userDto.setRole(existData.getRole());

            return new CustomOAuth2User(userDto);
        }
    }
}
