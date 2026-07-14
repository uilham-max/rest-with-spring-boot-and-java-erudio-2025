package br.com.erudio.integrationtests.controllers.withyaml;

import br.com.erudio.config.TestConfigs;
import br.com.erudio.integrationtests.dto.PersonDTO;
import br.com.erudio.integrationtests.dto.wrappers.xml.PagedModelPerson;
import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PersonControllerYamlTest extends AbstractIntegrationTest {

    private static RequestSpecification especification;
    private static PersonDTO person;
    private static YAMLMapper yamlMapper;

    @BeforeAll
    static void setUp() {
        person = new PersonDTO();
        yamlMapper = new YAMLMapper();
        yamlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Test
    @Order(1)
    void createTest() throws JsonProcessingException {
        mockPerson();
        especification = new RequestSpecBuilder()
                .setConfig(RestAssured.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs(
                                        MediaType.APPLICATION_YAML_VALUE,
                                        ContentType.TEXT)))
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var yaml = yamlMapper.writeValueAsString(person);

        var content = given(especification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                    .body(yaml)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                .extract().body().asString();

        PersonDTO createdPerson = yamlMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Linus", createdPerson.getFirstName());
        assertEquals("Torvalds", createdPerson.getLastName());
        assertEquals("Helsinki - Finland", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());

    }

    @Test
    @Order(2)
    void updateTest() throws JsonProcessingException {
        person.setLastName("Benedict Torvalds");
        var yaml = yamlMapper.writeValueAsString(person);

        var content = given(especification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                    .body(yaml)
                .when()
                    .put()
                .then()
                    .statusCode(200)
                .extract().body().asString();

        PersonDTO createdPerson = yamlMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Linus", createdPerson.getFirstName());
        assertEquals("Benedict Torvalds", createdPerson.getLastName());
        assertEquals("Helsinki - Finland", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());

    }


    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {

        var content = given(especification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                    .pathParam("id", person.getId())
                .when()
                    .get("{id}")
                .then()
                    .statusCode(200)
                .extract().body().asString();

        PersonDTO createdPerson = yamlMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Linus", createdPerson.getFirstName());
        assertEquals("Benedict Torvalds", createdPerson.getLastName());
        assertEquals("Helsinki - Finland", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());

    }


    @Test
    @Order(4)
    void disableTest() throws JsonProcessingException {

        var content = given(especification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                    .pathParam("id", person.getId())
                .when()
                    .patch("{id}")
                .then()
                    .statusCode(200)
                .extract().body().asString();

        PersonDTO createdPerson = yamlMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Linus", createdPerson.getFirstName());
        assertEquals("Benedict Torvalds", createdPerson.getLastName());
        assertEquals("Helsinki - Finland", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertFalse(createdPerson.getEnabled());

    }

    @Test
    @Order(5)
    void deleteTest(){
        given(especification)
            .pathParam("id", person.getId())
            .when()
                .delete("{id}")
            .then()
                .statusCode(204);
    }



    @Test
    @Order(6)
    void findAllTest() throws JsonProcessingException {

        var content = given(especification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract().body().asString();

        PagedModelPerson wrapper = yamlMapper.readValue(content, PagedModelPerson.class);
        List<PersonDTO> people = wrapper.getContent();

        PersonDTO personFive = people.get(5);

        assertNotNull(personFive.getId());
        assertTrue(personFive.getId() > 0);

        assertEquals("Addia", personFive.getFirstName());
        assertEquals("Marklund", personFive.getLastName());
        assertEquals("0212 Arkansas Way", personFive.getAddress());
        assertEquals("Female", personFive.getGender());
        assertFalse(personFive.getEnabled());

        PersonDTO personOne = people.get(0);

        assertNotNull(personOne.getId());
        assertTrue(personOne.getId() > 0);

        assertEquals("Abagail", personOne.getFirstName());
        assertEquals("Sessions", personOne.getLastName());
        assertEquals("4486 Warrior Center", personOne.getAddress());
        assertEquals("Female", personOne.getGender());
        assertTrue(personOne.getEnabled());

    }

    void findByNameTest() throws JsonProcessingException {

        var content = given(especification)
            .accept(MediaType.APPLICATION_YAML_VALUE)
            .pathParam("firstName", "and")
            .when()
                .get("findPeopleByName{firstName}")
            .then()
                .statusCode(200)
            .extract().body().asString();

        PagedModelPerson wrapper = yamlMapper.readValue(content, PagedModelPerson.class);
        List<PersonDTO> people = wrapper.getContent();

        PersonDTO personOne = people.get(0);

        assertNotNull(personOne.getId());
        assertTrue(personOne.getId() > 0);

        assertEquals("Cleavland", personOne.getFirstName());
        assertEquals("Noar", personOne.getLastName());
        assertEquals("8741 Pennsylvania Junction", personOne.getAddress());
        assertEquals("Male", personOne.getGender());
        assertTrue(personOne.getEnabled());

    }

    public void mockPerson() {
        person.setFirstName("Linus");
        person.setLastName("Torvalds");
        person.setAddress("Helsinki - Finland");
        person.setGender("Male");
        person.setEnabled(true);
    }

}