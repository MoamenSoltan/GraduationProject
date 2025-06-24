package org.example.backend.config.JWt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.example.backend.config.CustomUserDetailsService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;// to handle exceptions in filter

    private final JwtService jwtService;

    private final CustomUserDetailsService userDetailsService;

    public JwtFilter(HandlerExceptionResolver handlerExceptionResolver, JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.jwtService = jwtService;

        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,  HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader=request.getHeader("Authorization");
        String token=extractAccessTokenFromCookie(request);
        String username;

//        if(authHeader==null ||!authHeader.startsWith("Bearer "))
//        {
////            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden
////            response.setContentType("application/json");
////            response.getWriter().write("{"
////                    + "\"type\": \"about:blank\","
////                    + "\"title\": \"Forbidden\","
////                    + "\"status\": 403,"
////                    + "\"detail\": \"Missing authentication token. Please provide a valid JWT.\","
////                    + "\"error\": \"JWT token missing\""
////                    + "}");
////            response.getWriter().flush();
//            filterChain.doFilter(request,response);
//            return;
//
//        }
        if(token==null)
        {
            filterChain.doFilter(request,response);
            return;
        }
        try {
           // token = authHeader.substring(7);
            username = jwtService.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails
                        = userDetailsService.loadUserByUsername(username);

//                System.out.println("userDetails : " + userDetails);
                if (userDetails != null && jwtService.isValidateToken(token)) {
                    UsernamePasswordAuthenticationToken authentication
                            = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
            }

            filterChain.doFilter(request, response);
        }
        catch (ExpiredJwtException | MalformedJwtException | SignatureException |
               UnsupportedJwtException | IllegalArgumentException ex) {
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }

    private String extractAccessTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("accessToken")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
