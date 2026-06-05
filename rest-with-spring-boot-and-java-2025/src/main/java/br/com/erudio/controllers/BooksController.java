package br.com.erudio.controllers;

import br.com.erudio.data.dto.v1.BooksDTO;
import br.com.erudio.services.impl.BooksServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books/v1")
public class BooksController {

    // Suporte a Content Negotiotion, HATEOAS, Swagger

    private final BooksServiceImpl booksService;

    public BooksController(BooksServiceImpl booksService) {
        this.booksService = booksService;
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE }
    )
    public ResponseEntity<BooksDTO> bookCreate(@RequestBody BooksDTO booksDTO) {
        var bookSavedDTO = booksService.create(booksDTO);
        return new ResponseEntity<>(bookSavedDTO,HttpStatus.CREATED);
    }

    @GetMapping(
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE }
    )
    public List<BooksDTO> getAllBooks() {
        return booksService.getAll();
    }

    @GetMapping(value = "{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE }
    )
    public ResponseEntity<BooksDTO> findBooksById(@PathVariable("id") Long bookId) {
        BooksDTO bookFindsDTO = booksService.findById(bookId);
        return ResponseEntity.ok(bookFindsDTO) ;
    }

    @PutMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE }
    )
    public ResponseEntity<BooksDTO> updateBooks(@RequestBody BooksDTO booksDTO) {
        BooksDTO booksUpdated = booksService.update(booksDTO);
        return ResponseEntity.ok(booksUpdated);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBooks(@PathVariable("id") Long id){
        booksService.delete(id);
        return ResponseEntity.ok("Deleted");
    }

}
