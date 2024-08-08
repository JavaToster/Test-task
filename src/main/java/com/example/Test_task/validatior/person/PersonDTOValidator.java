package com.example.Test_task.validatior.person;

import com.example.Test_task.dao.person.PersonDAO;
import com.example.Test_task.dto.auth.PersonRegisterRequestDTO;
import com.example.Test_task.util.exceptions.person.PersonNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
@RequiredArgsConstructor
public class PersonDTOValidator implements Validator {
    private final PersonDAO personDAO;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(PersonRegisterRequestDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonRegisterRequestDTO personDTO = (PersonRegisterRequestDTO) target;

        try{
            personDAO.findByEmail(personDTO.getEmail());
        }catch (PersonNotFoundException ignored){
            //все окей, такого человека нет, можем регистрировать
            return;
        }

        errors.rejectValue("email", "", "человек с такой почтой уже существует");
    }
}
