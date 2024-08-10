package formLogin.form.logout;

import formLogin.form.jwt.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final JWTUtil jwtUtil;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.Authentication authentication) {
        System.out.println("jwt go to black list");
        String token = extractTokenFromCookies(request.getCookies());

        if (token != null) {
            jwtUtil.addToBlacklist(token);
        }

        System.out.println("Before SecurityContextHolder.getContext() = " + SecurityContextHolder.getContext());
        System.out.println("security context remove");
        SecurityContextHolder.clearContext();
        System.out.println("After SecurityContextHolder.getContext() = " + SecurityContextHolder.getContext());

        if (request.getSession(false) != null) {
            System.out.println("Sesstion 무효화");
            request.getSession(false).invalidate();
        }
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

