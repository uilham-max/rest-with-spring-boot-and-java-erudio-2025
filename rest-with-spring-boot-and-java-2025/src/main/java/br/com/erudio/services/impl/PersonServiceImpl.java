package br.com.erudio.services.impl;

import br.com.erudio.data.dto.v1.PersonDTO;
import br.com.erudio.data.dto.v2.PersonDTOV2;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.mapper.ObjectMapper;
import br.com.erudio.mapper.custom.PersonMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;
import br.com.erudio.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private static final Logger logger = LoggerFactory.getLogger( PersonServiceImpl.class.getName() );

    PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {this.personRepository = personRepository;}

    public PersonDTO create(PersonDTO personDTO) {
        logger.info("Creating new person: {}", personDTO);
        Person personSaved = personRepository.save(ObjectMapper.parseObject(personDTO, Person.class));
        return ObjectMapper.parseObject(personSaved, PersonDTO.class);
    }

//    public PersonDTOV2 createV2(PersonDTOV2 personDTOV2) {
//        logger.info("Creating new person: {}", personDTOV2);
//        Person personSaved = personRepository.save(PersonMapper.toPerson(personDTOV2));
//        return PersonMapper.personDTOV2(personSaved);
//    }

    public List<PersonDTO> findAll(){
        logger.info("Find all persons");
        List<Person> persons = personRepository.findAll();
        return ObjectMapper.parseListObjects(persons, PersonDTO.class);
    }

    public PersonDTO findById(Long id) {
        logger.info( "Find person by id" );
        Person personEntity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
        return ObjectMapper.parseObject(personEntity, PersonDTO.class);
    }

    public PersonDTO update(PersonDTO personDTO) {
        logger.info( "Updating person: {}", personDTO.toString() );
        Person person = personRepository.findById(personDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Person not found with this ID: " + personDTO.getId()));
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setAddress(personDTO.getAddress());
        person.setGender(personDTO.getGender());
        Person personUpdated = personRepository.save(person);
        return ObjectMapper.parseObject(personUpdated, PersonDTO.class);
    }

    public void delete(Long id) {
        logger.info( "Deleting Person with id {}", id );
        Person entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person not found with this ID: " + id));
        personRepository.delete( entity );
    }

}
