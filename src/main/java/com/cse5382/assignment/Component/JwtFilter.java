package com.cse5382.assignment.Component;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
    	
    	System.out.println("JwtFilter called: " + request.getRequestURI());

        final String authHeader = request.getHeader("Authorization");
        
        System.out.println("Authorization header: " + authHeader);
        
        

//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7);
//            System.out.println("Extracted token: " + token);
//            System.out.println("Is token valid: " + jwtUtil.isTokenValid(token));
//            System.out.println("Extracted username: " + jwtUtil.extractUsername(token));
//            
//            if (jwtUtil.isTokenValid(token)) {
//            	System.out.println("username 48:: " );
//                String username = jwtUtil.extractUsername(token);
//                System.out.println("username 48:: " + username);
//                UsernamePasswordAuthenticationToken authentication =
//                        new UsernamePasswordAuthenticationToken(username, null, null);
//                System.out.println("username 51:: " + authentication);
//                System.out.println("username 52:: " + request);
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                // Set authentication in context
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        }
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtUtil.isTokenValid(token)) {
                String username = jwtUtil.extractUsername(token);
                String role = jwtUtil.extractRole(token); // e.g. "READ_WRITE"

                System.out.println("✅ Authenticated username: " + username);
                System.out.println("✅ Role from token: " + role);

                // Spring Security expects roles prefixed with "ROLE_"
                List<GrantedAuthority> authorities = List.of(
                        new SimpleGrantedAuthority("ROLE_" + role)
                );

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}

