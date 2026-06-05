package br.com.erudio.services.impl;

import br.com.erudio.data.dto.v1.BooksDTO;
import br.com.erudio.exception.RequiredObjectIsNullException;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.mapper.ObjectMapper;
import br.com.erudio.model.Books;
import br.com.erudio.repository.BooksRepository;
import br.com.erudio.services.BooksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
public class BooksServiceImpl implements BooksService {

    Logger logger = LoggerFactory.getLogger(BooksServiceImpl.class);

    BooksRepository booksRepository;

    public BooksServiceImpl(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public BooksDTO create(BooksDTO booksDTO) {
        if(booksDTO == null) throw new RequiredObjectIsNullException();
        logger.info("Create a books {}", booksDTO.toString());
        Books books = ObjectMapper.parseObject(booksDTO, Books.class);
        Books booksSaved = booksRepository.save(books);
        return ObjectMapper.parseObject(booksSaved, BooksDTO.class);
    }

    @Override
    public List<BooksDTO> getAll() {
        logger.info("Get all books");
        List<Books> books = booksRepository.findAll();
        return ObjectMapper.parseListObjects(books, BooksDTO.class);
    }

    @Override
    public BooksDTO findById(Long idBooks) {
        Books bookReturned = booksRepository.findById(idBooks)
                .orElseThrow( () -> new ResourceNotFoundException("Book not found with id " + idBooks));
        logger.info("Find a books by id {}", bookReturned.toString());
        return ObjectMapper.parseObject(bookReturned, BooksDTO.class);
    }

    @Override
    public BooksDTO update(BooksDTO booksDTO) {
        if(booksDTO == null) throw new RequiredObjectIsNullException();
        logger.info("Update a books {}", booksDTO.toString());
        Books booksEntityReturned = booksRepository.findById(booksDTO.getId())
                .orElseThrow( () -> new ResourceNotFoundException("Book not found with id " + booksDTO.getId()));
        booksEntityReturned.setAuthor(booksDTO.getAuthor());
        booksEntityReturned.setTitle(booksDTO.getTitle());
        booksEntityReturned.setPrice(booksDTO.getPrice());
        booksEntityReturned.setLaunchDate(booksDTO.getLaunchDate());
        Books booksSaved = booksRepository.save(booksEntityReturned);
        return ObjectMapper.parseObject(booksSaved, BooksDTO.class);
    }

    @Override
    public void delete(Long id) {
        booksRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Book not found with id " + id));
        booksRepository.deleteById(id);
    }


}
