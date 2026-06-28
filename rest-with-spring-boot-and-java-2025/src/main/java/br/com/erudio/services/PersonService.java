package br.com.erudio.services;

import br.com.erudio.data.dto.v1.PersonDTO;
import br.com.erudio.data.dto.v2.PersonDTOV2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import java.util.List;

public interface PersonService {

    PersonDTO create(PersonDTO person);

//    PersonDTOV2 createV2(PersonDTOV2 person);

    PersonDTO update(PersonDTO person);

    void delete(Long id);

    PagedModel<EntityModel<PersonDTO>> findAll(Pageable pageable);

    PersonDTO findById(Long id);

    PersonDTO disablePerson(Long id);

}
