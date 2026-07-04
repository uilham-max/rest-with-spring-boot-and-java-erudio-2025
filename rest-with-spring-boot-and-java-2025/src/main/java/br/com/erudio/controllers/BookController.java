package br.com.erudio.controllers;

import br.com.erudio.controllers.docs.BookControllerDocs;
import br.com.erudio.data.dto.v1.BookDTO;
import br.com.erudio.services.impl.BookServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book/v1")
public class BookController implements BookControllerDocs {

    // Suporte a Content Negotiotion, HATEOAS, Swagger

    private final BookServiceImpl bookService;

    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE }
    )
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO bookDTO) {
        var bookSavedDTO = bookService.create(bookDTO);
        return new ResponseEntity<>(bookSavedDTO,HttpStatus.CREATED);
    }

    @GetMapping(
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE }
    )
    public ResponseEntity<PagedModel<EntityModel<BookDTO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = direction.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title", "author"));
        return ResponseEntity.ok(bookService.findAll(pageable));
    }

    @GetMapping(value = "{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE }
    )
    public ResponseEntity<BookDTO> findById(@PathVariable("id") Long bookId) {
        BookDTO bookFindsDTO = bookService.findById(bookId);
        return ResponseEntity.ok(bookFindsDTO) ;
    }

    @PutMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE }
    )
    public ResponseEntity<BookDTO> update(@RequestBody BookDTO bookDTO) {
        BookDTO bookUpdated = bookService.update(bookDTO);
        return ResponseEntity.ok(bookUpdated);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        bookService.delete(id);
        return ResponseEntity.ok("Deleted");
    }

}
