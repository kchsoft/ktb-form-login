package formLogin.form.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.Authentication authentication) throws IOException {
        // 쿠키 삭제
        System.out.println("cookie bye bye");
        Cookie cookie = new Cookie("Authorization", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // 쿠키 만료 시간 설정

        response.addCookie(cookie);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FOUND);
        String redirectUrl = objectMapper.writeValueAsString(new RedirectUrlJson("http://localhost:3000/"));
        System.out.println("redirectUrl = " + redirectUrl);
        response.getWriter().write(redirectUrl);
        response.getWriter().flush();
    }
}

