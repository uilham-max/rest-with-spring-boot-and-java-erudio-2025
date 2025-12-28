package br.com.erudio.mapper.custom;

import br.com.erudio.data.dto.v2.PersonDTOV2;
import br.com.erudio.model.Person;

import java.util.Date;


public class PersonMapper {

    public static PersonDTOV2 personDTOV2(Person person) {
        return new PersonDTOV2(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getGender(),
                person.getAddress(),
                new Date()
        );
    }

    public static Person toPerson(PersonDTOV2 personDTOV2){
        return new Person(
                personDTOV2.getId(),
                personDTOV2.getFirstName(),
                personDTOV2.getLastName(),
                personDTOV2.getAddress(),
                personDTOV2.getGender()
//                personDTOV2.getBirthDate()
        );

    }

}
