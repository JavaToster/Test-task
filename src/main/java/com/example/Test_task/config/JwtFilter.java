package com.example.Test_task.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.Test_task.security.JwtUtil;
import com.example.Test_task.services.person.PersonService;
import com.example.Test_task.util.exceptions.PersonNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final PersonService personService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")){
            String jwt = authHeader.substring(7);

            try {
                if (jwt.isBlank()) {
                    System.out.println(1);
                    response.sendError(400, "Invalid jwt token");

                } else {
                    String username = jwtUtil.validateTokenAndRetrieveClaim(jwt);
                    UserDetails userDetails = personService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    userDetails.getPassword(),
                                    userDetails.getAuthorities());


                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
                filterChain.doFilter(request, response);
            }catch (JWTVerificationException e){
                System.out.println(2);
                response.sendError(400, "Invalid jwt token");
            }catch (PersonNotFoundException e){
                response.sendError(401, "not authenticated user");
            }
        }else{
            response.sendError(401, "not authenticated user");
        }

        filterChain.doFilter(request, response);
    }
}
