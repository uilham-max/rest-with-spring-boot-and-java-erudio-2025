package br.com.erudio.controllers.docs;

import br.com.erudio.data.dto.v1.BookDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface BookControllerDocs {

    @Operation(summary = "Adds a new Book",
            description = "Creates a Book",
            tags = {"Book"},
            responses = {
                    @ApiResponse(
                            description = "Sucess",
                            responseCode = "200",
                            content = {@Content(schema = @Schema(implementation = BookDTO.class))}
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            })
    ResponseEntity<BookDTO> create(@RequestBody BookDTO BookDTO);

    @Operation(summary = "Find All Book",
            description = "Finds All Book",
            tags = {"Book"},
            responses = {
                    @ApiResponse(
                            description = "Sucess",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = BookDTO.class))
                                    )
                            }
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            })
    List<BookDTO> findAll();

    @Operation(summary = "Finds a Book",
            description = "Find a specific Book by your ID",
            tags = {"Book"},
            responses = {
                    @ApiResponse(
                            description = "Sucess",
                            responseCode = "200",
                            content = {@Content(schema = @Schema(implementation = BookDTO.class))}
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            })
    ResponseEntity<BookDTO> findById(@PathVariable("id") Long id);

    @Operation(summary = "Update a Book",
            description = "Update a specific Book by your ID",
            tags = {"Book"},
            responses = {
                    @ApiResponse(
                            description = "Sucess",
                            responseCode = "200",
                            content = {@Content(schema = @Schema(implementation = BookDTO.class))}
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            })
    ResponseEntity<BookDTO> update(@RequestBody BookDTO BookDTO);

    @Operation(summary = "Delete a Book",
            description = "Delete a specific Book by your ID",
            tags = {"Book"},
            responses = {
                    @ApiResponse(
                            description = "Sucess",
                            responseCode = "200",
                            content = {@Content(schema = @Schema(implementation = BookDTO.class))}
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            })
    ResponseEntity<?> delete(@PathVariable Long id);
}
