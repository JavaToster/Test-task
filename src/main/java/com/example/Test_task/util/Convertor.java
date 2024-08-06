package com.example.Test_task.util;

import com.example.Test_task.dto.auth.PersonRegisterRequestDTO;
import com.example.Test_task.models.person.Person;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Convertor {
    private final ModelMapper modelMapper;

    public Person convertToPerson(PersonRegisterRequestDTO personDTO){
        return modelMapper.map(personDTO, Person.class);
    }
}
