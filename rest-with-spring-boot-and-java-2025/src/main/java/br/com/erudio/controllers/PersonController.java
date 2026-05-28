package br.com.erudio.controllers;

import br.com.erudio.data.dto.v1.PersonDTO;
import br.com.erudio.data.dto.v2.PersonDTOV2;
import br.com.erudio.services.impl.PersonServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

    private PersonServiceImpl personServices;

    public PersonController(PersonServiceImpl personServices) {
        this.personServices = personServices;
    }

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE })
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO personDTO) {
        PersonDTO entity = personServices.create(personDTO);
        return ResponseEntity.ok(entity);
    }


    @GetMapping(
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE })
    public List<PersonDTO> findAll(){
        return personServices.findAll();
    }

    @GetMapping(value = "/{id}",
            produces =  {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE })
    public PersonDTO findById(@PathVariable("id") Long id) {
        PersonDTO personDTO = personServices.findById(id);
//        personDTO.setBirthday(new Date());
//        personDTO.setPhoneNumber("+55 (53) 99703-2373");
//        personDTO.setPhoneNumber("");
//        personDTO.setLastName(null);
//        personDTO.setSensitiveData("Foo Bar");
        return personDTO;
    }

    @PutMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE })
    public PersonDTO update(@RequestBody PersonDTO personDTO) {
        return personServices.update(personDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        personServices.delete(id);
        return ResponseEntity.noContent().build();
    }




}
