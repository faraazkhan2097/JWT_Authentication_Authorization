package com.example.bookstrore.config;

import com.example.bookstrore.filter.JwtAuthenticationFilter;
import com.example.bookstrore.service.JwtService;
import com.example.bookstrore.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsServiceImpl _userDetailsServiceImpl;
    private final JwtAuthenticationFilter _jwtAuthenticationFilter;

    public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl, JwtAuthenticationFilter jwtAuthenticationFilter){
        this._userDetailsServiceImpl = userDetailsServiceImpl;
        this._jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req -> req.requestMatchers("/bookstore/login/**", "/bookstore/register/**")
                                .permitAll()
                                .requestMatchers("/bookstore/admin_only/**").hasAuthority("ADMIN")
                                .anyRequest()
                                .authenticated()
                ).userDetailsService(_userDetailsServiceImpl)
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(_jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
}
