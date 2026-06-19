package br.com.erudio.services.impl;

import br.com.erudio.controllers.PersonController;
import br.com.erudio.data.dto.v1.PersonDTO;
import br.com.erudio.exception.RequiredObjectIsNullException;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.mapper.ObjectMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;
import br.com.erudio.services.PersonService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServiceImpl implements PersonService {

    private static final Logger logger = LoggerFactory.getLogger( PersonServiceImpl.class.getName() );

    PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonDTO create(PersonDTO personDTO) {
        if(personDTO == null) throw new RequiredObjectIsNullException();
        logger.info("Creating new person: {}", personDTO);
        Person personSaved = personRepository.save(ObjectMapper.parseObject(personDTO, Person.class));
        var dto = ObjectMapper.parseObject(personSaved, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public List<PersonDTO> findAll(){
        logger.info("Find all persons");
        List<Person> persons = personRepository.findAll();
        var personsDTOs = ObjectMapper.parseListObjects(persons, PersonDTO.class);
        personsDTOs.forEach(this::addHateoasLinks);
        return personsDTOs;
    }

    public PersonDTO findById(Long id) {
        logger.info( "Find person by id" );
        Person personEntity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
        var dto = ObjectMapper.parseObject(personEntity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDTO update(PersonDTO personDTO) {
        if(personDTO == null) throw new RequiredObjectIsNullException();
        logger.info( "Updating person: {}", personDTO.toString() );
        Person person = personRepository.findById(personDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Person not found with this ID: " + personDTO.getId()));
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setAddress(personDTO.getAddress());
        person.setGender(personDTO.getGender());
        Person personUpdated = personRepository.save(person);
        var dto = ObjectMapper.parseObject(personUpdated, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id) {
        logger.info( "Deleting Person with id {}", id );
        Person entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person not found with this ID: " + id));
        personRepository.delete( entity );
    }

    @Transactional
    @Override
    public PersonDTO disablePerson(Long id) {
        logger.info( "Disabling Person with id {}", id );
        personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with this ID: " + id));
        personRepository.disablePerson(id);
        Person entity = personRepository.findById(id).get();
        var dto = ObjectMapper.parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).disablePerson(dto.getId())).withRel("disable").withType("PATCH"));

    }

}
