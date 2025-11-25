// src/main/java/com/salon/backend_salon_management/config/SecurityConfig.java
package com.salon.backend_salon_management.config;

import com.salon.backend_salon_management.security.JwtAuthenticationFilter;
import com.salon.backend_salon_management.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;

    private static final String[] PUBLIC_ENDPOINTS = {
            "/api/auth/admin/login",
            "/api/auth/customer/login",
            "/api/auth/customer/register",
            "/api/auth/provider/login",
            "/api/auth/provider/register",
            "/api/salons/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers("/api/customer/**").hasRole("CUSTOMER")
                        .requestMatchers("/api/provider/**").hasRole("PROVIDER")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())

                .addFilterBefore((req, res, chain) -> {

                    HttpServletRequest request = (HttpServletRequest) req;
                    String path = request.getServletPath();

                    // Skip JWT filter for public endpoints only
                    for (String publicPath : PUBLIC_ENDPOINTS) {
                        if (path.startsWith(publicPath.replace("/**", ""))) {
                            chain.doFilter(req, res);
                            return;
                        }
                    }

                    jwtAuthFilter.doFilter(req, res, chain);

                }, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ==================================================
    // ONLY CORS OPENED — SECURITY LOGIC UNCHANGED
    // ==================================================
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedOriginPattern("*"); // allow all origins
        config.addAllowedHeader("*"); // allow all request headers
        config.addAllowedMethod("*"); // allow all HTTP methods
        config.setAllowCredentials(true); // allow cookies / authorization
        config.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
