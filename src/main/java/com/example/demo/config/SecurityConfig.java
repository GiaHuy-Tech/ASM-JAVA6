package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.example.demo.service.AccountService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AccountService userService;
    private final CustomSuccessHandler successHandler;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(AccountService userService,
                          CustomSuccessHandler successHandler,
                          CustomAccessDeniedHandler accessDeniedHandler) {
        this.userService = userService;	
        this.successHandler = successHandler;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        // ⚠ Dùng NoOp cho dev (so sánh plain text)
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // ✅ Xử lý khi đăng nhập thất bại
    @Bean
    public AuthenticationFailureHandler authFailureHandler() {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request,
                                                HttpServletResponse response,
                                                org.springframework.security.core.AuthenticationException exception)
                    throws IOException, ServletException {
                String redirectUrl = "/login?error";
                if (exception instanceof LockedException) {
                    redirectUrl = "/login?locked";
                } else if (exception instanceof UsernameNotFoundException) {
                    redirectUrl = "/login?notfound";
                }
                response.sendRedirect(redirectUrl);
            }
        };
    }

    // ✅ Cấu hình quyền truy cập
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            CustomAuthFailureHandler customAuthFailureHandler) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // ✅ Các trang public
                        .requestMatchers("/", "/login", "/register", "/forgot",
                                "/css/**", "/js/**", "/images/**", "/vendor/**").permitAll()

                        // ✅ Các trang chỉ USER được truy cập
                        .requestMatchers("/account/**", "/cart/**", "/checkout/**").hasRole("USER")

                        // ✅ Các trang chỉ ADMIN được truy cập
                        .requestMatchers(
                                "/stats/**",
                                "/product-mana/**",
                                "/admin/cart/**",
                                "/user-mana/**",
                                "/cata-mana/**"
                        ).hasRole("ADMIN")

                        // ✅ Các trang khác cần đăng nhập
                        .anyRequest().authenticated()
                )

                // ✅ Form đăng nhập
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(successHandler)
                        .failureHandler(customAuthFailureHandler)
                        .permitAll()
                )

                // ✅ Đăng xuất
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )

                // ✅ Khi bị chặn quyền → custom handler
                .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler))
                .build();
    }
}
