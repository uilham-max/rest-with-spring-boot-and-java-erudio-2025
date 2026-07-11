package br.com.erudio.integrationtests.controllers.cors.withjson;

import br.com.erudio.config.TestConfigs;
import br.com.erudio.integrationtests.dto.BookDTO;
import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BookControllerCorsTest extends AbstractIntegrationTest {

    private static RequestSpecification especification;
    private static BookDTO book;
    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setUp() {
        book = new BookDTO();
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Test
    @Order(1)
    void create() throws JsonProcessingException {
        mockBook();
        especification = new RequestSpecBuilder()
            .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
                .setAccept(MediaType.APPLICATION_JSON_VALUE)
                .setBasePath("/api/book/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();

        var content = given(especification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(book)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                .extract().body().asString();

        BookDTO createdBook = objectMapper.readValue(content, BookDTO.class);
        book = createdBook;

        assertNotNull(createdBook.getId());
        assertNotNull(createdBook.getTitle());
        assertNotNull(createdBook.getAuthor());
        assertNotNull(createdBook.getPrice());
        assertNotNull(createdBook.getLaunchDate());

        assertTrue(createdBook.getId() > 0);

        assertEquals("Os 11 segredos de líderes de TI altamente influentes", createdBook.getTitle());
        assertEquals("Marc J. Schiller",createdBook.getAuthor());
        assertEquals(new BigDecimal("39.00"), createdBook.getPrice());
        assertEquals(Timestamp.valueOf(LocalDateTime.parse("2017-11-07T15:09:01.674")), createdBook.getLaunchDate());
    }

    @Test
    @Order(2)
    void craeteWithWrongOrigin(){
        especification = new RequestSpecBuilder()
            .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SEMERU)
                .setBasePath("/api/book/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();

        var content = given(especification)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(book)
            .when()
                .post()
            .then()
                .statusCode(403)
            .extract().body().asString();

        assertEquals("Invalid CORS request", content);

    }

    private void mockBook() {
        book.setTitle("Os 11 segredos de líderes de TI altamente influentes");
        book.setAuthor("Marc J. Schiller");
        book.setPrice(new BigDecimal("39.00"));
        book.setLaunchDate(Timestamp.valueOf(LocalDateTime.parse("2017-11-07T15:09:01.674")));
    }

    @Test
    @Order(3)
    void findById() throws JsonProcessingException {
        especification = new RequestSpecBuilder()
            .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
                .setBasePath("/api/book/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();

        var content = given(especification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .pathParam("id", book.getId())
                .when()
                    .get("{id}")
                .then()
                    .statusCode(200)
                .extract().body().asString();

            BookDTO createdBook = objectMapper.readValue(content, BookDTO.class);
            book = createdBook;

            assertNotNull(createdBook.getId());
            assertNotNull(createdBook.getTitle());
            assertNotNull(createdBook.getAuthor());
            assertNotNull(createdBook.getPrice());
            assertNotNull(createdBook.getLaunchDate());

            assertTrue(createdBook.getId() > 0);

            assertEquals("Os 11 segredos de líderes de TI altamente influentes", createdBook.getTitle());
            assertEquals("Marc J. Schiller",createdBook.getAuthor());
            assertEquals(new BigDecimal("39.00"), createdBook.getPrice());
            assertEquals(Timestamp.valueOf(LocalDateTime.parse("2017-11-07T15:09:01.674")), createdBook.getLaunchDate());

    }

    @Test
    @Order(4)
    void findByIdWithWrongOrigin(){
        especification = new RequestSpecBuilder()
            .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SEMERU)
            .setBasePath("/api/book/v1")
            .setPort(TestConfigs.SERVER_PORT)
            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
            .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();

        var result = given(especification)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", book.getId())
            .when()
                .get("{id}")
            .then()
                .statusCode(403)
                    .extract().body().asString();

        assertEquals("Invalid CORS request", result);
    }

}