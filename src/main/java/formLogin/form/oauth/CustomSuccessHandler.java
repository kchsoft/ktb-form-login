package formLogin.form.oauth;

import formLogin.form.dto.CustomOAuth2User;
import formLogin.form.jwt.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("here is oauth success");
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String username = oAuth2User.getUsername();

        Collection<? extends GrantedAuthority> authorities = oAuth2User.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(username, role, 60 * 60 * 10L);
        response.addCookie(createCookie("Authorization",token));
        response.sendRedirect("http://localhost:3000/");
    }

    private Cookie createCookie(String key,String value) {
        Cookie cookie = new Cookie(key,value);
        cookie.setMaxAge(60*60*60);
        cookie.setPath("/");
//        cookie.setSecure(true); // https만 사용할 수 있게 해줌
        cookie.setHttpOnly(true); // http만 사용 , js가 쿠키에서 토큰 탈취 못 함.

        return cookie;
    }

}
