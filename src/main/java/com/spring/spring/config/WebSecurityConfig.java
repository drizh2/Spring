package com.spring.spring.config;

import com.spring.spring.domain.User;
import com.spring.spring.service.CustomPasswordEncoder;
import com.spring.spring.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    public void authenticationProvider(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder.getPasswordEncoder());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/registration", "/static/**", "/activate/*", "/user/profile").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> {
                            try {
                                form
                                            .loginPage("/login")
                                            .defaultSuccessUrl("/main", true)
                                            .permitAll()
                                        .and()
                                            .rememberMe();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }
}

