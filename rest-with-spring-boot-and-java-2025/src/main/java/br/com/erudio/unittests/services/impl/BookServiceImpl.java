package br.com.erudio.unittests.services.impl;

import br.com.erudio.controllers.BookController;
import br.com.erudio.data.dto.v1.BookDTO;
import br.com.erudio.exception.RequiredObjectIsNullException;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.mapper.ObjectMapper;
import br.com.erudio.model.Book;
import br.com.erudio.repository.BookRepository;
import br.com.erudio.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServiceImpl implements BookService {

    Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDTO create(BookDTO bookDTO) {
        if(bookDTO == null) throw new RequiredObjectIsNullException();
        logger.info("Create new book {}", bookDTO);
        Book bookSaved = bookRepository.save(ObjectMapper.parseObject(bookDTO, Book.class));
        var dto = ObjectMapper.parseObject(bookSaved, BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Override
    public List<BookDTO> findAll() {
        logger.info("Find all books");
        List<Book> books = bookRepository.findAll();
        var booksDTOs = ObjectMapper.parseListObjects(books, BookDTO.class);
        booksDTOs.forEach(this::addHateoasLinks);
        return booksDTOs;
    }

    @Override
    public BookDTO findById(Long id) {
        logger.info("Find book by id");
        Book bookEntity = bookRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Book not found with id " + id));
        var dto = ObjectMapper.parseObject(bookEntity, BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Override
    public BookDTO update(BookDTO bookDTO) {
        if(bookDTO == null) throw new RequiredObjectIsNullException();
        logger.info("Updating book {}", bookDTO.toString());
        Book book = bookRepository.findById(bookDTO.getId())
                .orElseThrow( () -> new ResourceNotFoundException("Book not found with id " + bookDTO.getId()));
        book.setAuthor(bookDTO.getAuthor());
        book.setTitle(bookDTO.getTitle());
        book.setPrice(bookDTO.getPrice());
        book.setLaunchDate(bookDTO.getLaunchDate());
        Book bookUpdated = bookRepository.save(book);
        var dto = ObjectMapper.parseObject(bookUpdated, BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting book with id {}", id);
        Book entity = bookRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Book not found with id " + id));
        bookRepository.delete( entity );
    }

    public void addHateoasLinks(BookDTO dto) {
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(BookController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(BookController.class).update(dto)).withRel("update").withType("PUT"));

    }


}
