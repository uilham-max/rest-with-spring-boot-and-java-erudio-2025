package br.com.erudio.services;

import br.com.erudio.data.dto.v1.PersonDTO;
import br.com.erudio.data.dto.v2.PersonDTOV2;

import java.util.List;

public interface PersonService {

    PersonDTO create(PersonDTO person);

//    PersonDTOV2 createV2(PersonDTOV2 person);

    PersonDTO update(PersonDTO person);

    void delete(Long id);

    List<PersonDTO> findAll();

    PersonDTO findById(Long id);

    PersonDTO disablePerson(Long id);

}
