package org.example.backend.config;

import org.example.backend.config.JWt.JwtFilter;
import org.example.backend.config.JWt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfig {
    private static final String[] PUBLIC_URLS = {
            "/auth/login", "/auth/register","auth/refreshToken","auth/refresh_token","/forgot-password/**","/task/course/**",
            "/test/**", "/api/**",
            "/v2/api-docs", "/v3/api-docs",
            "/v3/api-docs/**", "/swagger-resources",
            "/swagger-resources/**", "/configuration/ui",
            "/configuration/security", "/swagger-ui/**",
            "/webjars/**", "/swagger-ui.html"
    };

    private final CustomUserDetailsService userDetailsService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;

    }

    @Bean
    public JwtFilter getJwtFilter(JwtService jwtService) {
        return new JwtFilter(handlerExceptionResolver,jwtService,userDetailsService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security,JwtService jwtService) throws Exception {
        security
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .requestMatchers("/admin/**","/api/**","/instructor/course/**").permitAll()
                        .requestMatchers("/auth/data").hasAuthority("ROLE_INSTRUCTOR")
                        .requestMatchers("/instructor/**","/task/create").hasAuthority("ROLE_INSTRUCTOR")
                        .requestMatchers("/student/**").hasAuthority("ROLE_STUDENT")
                        .anyRequest().authenticated()
                )
//                .httpBasic(Customizer.withDefaults())
//                .formLogin(f->f.permitAll())
                .addFilterBefore(getJwtFilter(jwtService), UsernamePasswordAuthenticationFilter.class)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
//                .exceptionHandling(e -> e
//                        .accessDeniedHandler((request, response, accessDeniedException) -> {
//                            response.setStatus(HttpStatus.FORBIDDEN.value());
//                            response.setContentType("application/json");
//                            response.getWriter().write("{\"status\": 403, \"error\": \"Forbidden\", \"message\": \"Access to the resource is prohibited.\"}");
//                        }));

        return security.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOrigins(Arrays.asList(
                    "http://localhost:3000",
                    "http://localhost:4200",
                    "http://localhost:5173"
            ));
            corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
            corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
            corsConfiguration.setMaxAge(3600L);
            return corsConfiguration;
        };
    }
}
