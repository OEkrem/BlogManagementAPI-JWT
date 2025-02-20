package com.oekrem.jwt_deneme.security;

import com.oekrem.jwt_deneme.services.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthService authenticationService;

    @Override
    protected void doFilterInternal
            (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try{
            String bearerToken = request.getHeader("Authorization");
            String token = null;
            if(bearerToken != null && bearerToken.startsWith("Bearer ")){
                token = bearerToken.substring(7);
            }
            if (token != null) {
                UserDetails userDetails = authenticationService.validateToken(token);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null,
                        userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                if(userDetails instanceof BlogUserDetails){
                    request.setAttribute("userId", ((BlogUserDetails) userDetails).getId());
                }
            }
        }catch (Exception e){
            log.warn("Received invalid JWT token: ", e);
        }

        filterChain.doFilter(request, response);
    }
}
