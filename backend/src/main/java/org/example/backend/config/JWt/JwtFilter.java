package org.example.backend.config.JWt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.backend.config.CustomUserDetailsService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final CustomUserDetailsService userDetailsService;

    public JwtFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;

        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader=request.getHeader("Authorization");
        String token;
        String username;

        if(authHeader==null ||!authHeader.startsWith("Bearer "))
        {
            filterChain.doFilter(request,response);
            return;

        }

        token=authHeader.substring(7);
        username = jwtService.extractUsername(token);

        if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UserDetails userDetails
                    =userDetailsService.loadUserByUsername(username);


            if(userDetails!= null &&jwtService.isValidateToken(token))
            {
                UsernamePasswordAuthenticationToken authentication
                        =new UsernamePasswordAuthenticationToken(username,null,userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }

        filterChain.doFilter(request,response);
    }
}
