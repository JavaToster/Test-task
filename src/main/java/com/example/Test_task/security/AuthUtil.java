package com.example.Test_task.security;


import com.example.Test_task.dto.auth.AuthenticationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthUtil {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public String getJWTToken(AuthenticationDTO authDTO, BindingResult errors){
        if(errors.hasErrors()){
            List<String> errorNames = new ArrayList<>();
            for(ObjectError error: errors.getAllErrors()){
                errorNames.add(error.getDefaultMessage());
            }
            throw new BadCredentialsException(errorNames.stream().collect(Collectors.joining(";")));
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword());
        authenticationManager.authenticate(authToken);

        return jwtUtil.generateToken(authDTO.getEmail());
    }
}
