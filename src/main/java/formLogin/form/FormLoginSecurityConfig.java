package formLogin.form;

import formLogin.form.jwt.JWTFilter;
import formLogin.form.jwt.JWTUtil;
import formLogin.form.jwt.LoginFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class FormLoginSecurityConfig {

    private final AuthenticationConfiguration configuration;
    private final JWTUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors
                        .configurationSource(new CustomCorsConfigurationSource())) // security 필터 차원에서의 cors 방지
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/join").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login.disable())
//                .formLogin(form -> form
//                         .loginPage("/loginPage")
//                        .loginProcessingUrl("/loginProcess")
//                        .defaultSuccessUrl("/loginSuccess")
//                        .permitAll()
//                )
                .sessionManagement(session -> session //jwt는 항상 세션을 stateless 상태로 관리한다.
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterAt(new LoginFilter(authenticationManager(configuration),jwtUtil), UsernamePasswordAuthenticationFilter.class) // 원하는 자리에 필터 주입(필터 대체)
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class)
                .build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.withUsername("user")
//                .password(passwordEncoder().encode("password"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user2 = User.withUsername("testName")
//                .password(passwordEncoder().encode("testPassWord"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user,user2);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
