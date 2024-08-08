package com.example.Test_task.services.person;

import com.example.Test_task.dto.auth.PersonRegisterRequestDTO;
import com.example.Test_task.dto.auth.RegisterResponseDTO;
import com.example.Test_task.models.person.Person;
import com.example.Test_task.repositories.person.PersonRepository;
import com.example.Test_task.security.JwtUtil;
import com.example.Test_task.util.Convertor;
import com.example.Test_task.util.exceptions.person.PersonNotFoundException;
import com.example.Test_task.util.exceptions.person.PersonRegisterException;
import com.example.Test_task.validatior.person.PersonDTOValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.Test_task.security.PersonDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService implements UserDetailsService {
    private final PersonRepository personRepository;
    private final PersonDTOValidator personDTOValidator;
    private final Convertor convertor;
    private final PasswordEncoder encoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws PersonNotFoundException {
        Person person = personRepository.findByEmail(username).orElseThrow(() -> new PersonRegisterException("person with email not found"));

        return new PersonDetails(person);
    }

    public RegisterResponseDTO register(PersonRegisterRequestDTO personDTO, BindingResult bindingResult) {
        personDTOValidator.validate(personDTO, bindingResult);

        if(bindingResult.hasErrors()){
            List<String> errorMessages = new ArrayList<>();
            for(ObjectError error: bindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            throw new PersonRegisterException(errorMessages.stream().collect(Collectors.joining(";")));
        }

        Person person = convertor.convertToPerson(personDTO);

        person.setPassword(encoder.encode(person.getPassword()));

        RegisterResponseDTO registerResponseDTO = RegisterResponseDTO.builder()
                        .email(personDTO.getEmail())
                        .build();

        personRepository.save(person);

        return registerResponseDTO;
    }
}
