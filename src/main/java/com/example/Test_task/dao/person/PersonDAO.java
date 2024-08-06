package com.example.Test_task.dao.person;

import com.example.Test_task.models.person.Person;
import com.example.Test_task.repositories.person.PersonRepository;
import com.example.Test_task.util.exceptions.PersonNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersonDAO {
    private final PersonRepository personRepository;

    public Person findByEmail(String email){
        return personRepository.findByEmail(email).orElseThrow(PersonNotFoundException::new);
    }
}
