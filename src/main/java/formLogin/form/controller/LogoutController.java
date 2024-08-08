package formLogin.form.controller;

import formLogin.form.jwt.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LogoutController {

    private final JWTUtil jwtUtil;

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        // JWT 블랙리스트에 추가
        String token = extractTokenFromCookies(request.getCookies());
        if (token != null) {
            jwtUtil.addToBlacklist(token);
        }

        // SecurityContextHolder 정리
        SecurityContextHolder.clearContext();

        // 쿠키 삭제
        Cookie cookie = new Cookie("Authorization", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // 쿠키 만료 시간 설정
        response.addCookie(cookie);
    }

    private String extractTokenFromCookies(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Authorization".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
