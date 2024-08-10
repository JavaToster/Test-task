package com.example.Test_task.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.Test_task.dto.ErrorResponseDTO;
import com.example.Test_task.dto.auth.AuthenticationDTO;
import com.example.Test_task.dto.auth.PersonRegisterRequestDTO;
import com.example.Test_task.dto.auth.RegisterResponseDTO;
import com.example.Test_task.security.AuthUtil;
import com.example.Test_task.services.person.PersonService;
import com.example.Test_task.util.exceptions.person.PersonRegisterException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(
        name = "authentication controller",
        description = "use jwt token for authentication and authorization"
)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final PersonService personService;
    private final AuthUtil authUtil;

    @Operation(
            summary = "register new user",
            description = "get person registration dto(email and password), validate it and return email of registered user"
    )
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody @Valid PersonRegisterRequestDTO personDTO, BindingResult bindingResult){
        RegisterResponseDTO registerResponse = personService.register(personDTO, bindingResult);
        return new ResponseEntity<>(registerResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "log in user, check is user registered",
            description = "check email and password inner AuthenticationDTO, validate it. If user right enter email and password give jwt token, else throw JWTVerificationException"
    )
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> performLogin(@RequestBody @Valid AuthenticationDTO authDTO, BindingResult errors){
        String token = authUtil.getJWTToken(authDTO, errors);
        return new ResponseEntity<>(Map.of("jwt_token", token), HttpStatus.OK);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> exceptionHandle(JWTVerificationException e){
        return new ResponseEntity<>(new ErrorResponseDTO("token not validate"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> exceptionHandle(PersonRegisterException e){
        return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> exceptionHandle(BadCredentialsException e){
        return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
