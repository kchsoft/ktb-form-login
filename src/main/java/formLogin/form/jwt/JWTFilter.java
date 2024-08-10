package formLogin.form.jwt;

import formLogin.form.dto.CustomOAuth2User;
import formLogin.form.dto.UserDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("here is jwt filter - check jwt token");
        String authorization = null;
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            System.out.println("cookies is null");
            filterChain.doFilter(request,response);
            return;
        }

        System.out.println("start get cookies name");
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName() + " : " + cookie.getValue());
            if (cookie.getName().equals("Authorization")) {
                authorization = cookie.getValue();
            }
        }

        //Authorization 헤더 검증
        if (authorization == null) {
            System.out.println("token == null");
            filterChain.doFilter(request, response);
            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        //토큰
        String token = authorization;

        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            System.out.println("token expired");
            filterChain.doFilter(request, response);
             //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        UserDto userDto = new UserDto();
        userDto.setName(username);
        userDto.setRole(role);

        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDto);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
