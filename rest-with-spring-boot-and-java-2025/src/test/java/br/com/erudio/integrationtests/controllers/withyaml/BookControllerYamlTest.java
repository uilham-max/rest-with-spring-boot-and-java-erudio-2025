package br.com.erudio.integrationtests.controllers.withyaml;

import br.com.erudio.config.TestConfigs;
import br.com.erudio.integrationtests.dto.BookDTO;
import br.com.erudio.integrationtests.dto.wrappers.xml.PagedModelBook;
import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BookControllerYamlTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static BookDTO book;
    private static YAMLMapper yamlMapper;

    @BeforeAll
    static void setUp() {
        book = new BookDTO();
        yamlMapper = new YAMLMapper();
        yamlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    void mockBook(){
        book.setTitle("Os 11 segredos de líderes de TI altamente influentes");
        book.setAuthor("Marc J. Schiller");
        book.setPrice(new BigDecimal("39.00"));
        book.setLaunchDate(Timestamp.valueOf(LocalDateTime.parse("2017-11-07T15:09:01.674")));
    }

    @Test
    @Order(1)
    void createTest() throws JsonProcessingException {
        mockBook();
        specification = new RequestSpecBuilder()
            .setConfig(RestAssured.config()
                    .encoderConfig(EncoderConfig.encoderConfig()
                            .encodeContentTypeAs(
                                    MediaType.APPLICATION_YAML_VALUE,
                                    ContentType.TEXT)))
            .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
                .setBasePath("/api/book/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();

        var result = given(specification)
            .contentType(MediaType.APPLICATION_YAML_VALUE)
            .accept(MediaType.APPLICATION_YAML_VALUE)
                .body(yamlMapper.writeValueAsString(book))
            .when()
                .post()
            .then()
                .statusCode(200)
            .extract().body().asString();

        BookDTO createdBook = yamlMapper.readValue(result, BookDTO.class);
        book = createdBook;

        assertNotNull(createdBook.getId());
        assertTrue(createdBook.getId() > 0);

        assertEquals("Os 11 segredos de líderes de TI altamente influentes", createdBook.getTitle());
        assertEquals("Marc J. Schiller", createdBook.getAuthor());
        assertEquals(new BigDecimal("39.00"), createdBook.getPrice());
        assertEquals(Timestamp.valueOf(LocalDateTime.parse("2017-11-07T15:09:01.674")), createdBook.getLaunchDate());

    }

    @Test
    @Order(2)
    void updateTest() throws JsonProcessingException {

        book.setPrice(new BigDecimal("49.00"));

        var content = given(specification)
            .contentType(MediaType.APPLICATION_YAML_VALUE)
            .accept(MediaType.APPLICATION_YAML_VALUE)
            .body(yamlMapper.writeValueAsString(book))
            .when()
                .put()
            .then()
                .statusCode(200)
            .extract().body().asString();

        BookDTO createdBook = yamlMapper.readValue(content, BookDTO.class);
        book = createdBook;

        assertNotNull(createdBook.getId());
        assertTrue(createdBook.getId() > 0);
        assertEquals(new BigDecimal("49.00"), createdBook.getPrice());

    }

    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {
        var content = given(specification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .pathParam("id", book.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract().body().asString();

        BookDTO createdBook = yamlMapper.readValue(content, BookDTO.class);
        book = createdBook;

        assertNotNull(createdBook.getId());
        assertTrue(createdBook.getId() > 0);

        assertEquals("Os 11 segredos de líderes de TI altamente influentes",createdBook.getTitle());
        assertEquals("Marc J. Schiller", createdBook.getAuthor());
        assertEquals(new BigDecimal("49.00"), createdBook.getPrice());
        assertEquals(Timestamp.valueOf(LocalDateTime.parse("2017-11-07T15:09:01.674")), createdBook.getLaunchDate());
    }

    @Test
    @Order(4)
    void deleteTest() {
        given(specification)
                .pathParam("id", book.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(5)
    void findAllTest() throws JsonProcessingException {
        var content = given(specification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .queryParams("page", 0, "size", 12, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().body().asString();

        PagedModelBook wrapper = yamlMapper.readValue(content, PagedModelBook.class);
        List<BookDTO> books = wrapper.getContent();

        BookDTO bookFive = books.get(5);

        assertNotNull(bookFive.getId());
        assertTrue(bookFive.getId() > 0);

        assertEquals("Engenharia de Software: uma abordagem profissional", bookFive.getTitle());
        assertEquals("Roger S. Pressman", bookFive.getAuthor());
        assertEquals(new BigDecimal("56.00"), bookFive.getPrice());
        assertEquals(Timestamp.valueOf(LocalDateTime.parse("2017-11-07T15:09:01.674")), bookFive.getLaunchDate());

        BookDTO bookOne = books.get(0);

        assertNotNull(bookOne.getId());
        assertTrue(bookOne.getId() > 0);

        assertEquals("Big Data: como extrair volume, variedade, velocidade e valor da avalanche de informação cotidiana", bookOne.getTitle());
        assertEquals("Viktor Mayer-Schonberger e Kenneth Kukier", bookOne.getAuthor());
        assertEquals(new BigDecimal("54.00"), bookOne.getPrice());
        assertEquals(Timestamp.valueOf(LocalDateTime.parse("2017-11-07T15:09:01.674")), bookOne.getLaunchDate());

    }
}