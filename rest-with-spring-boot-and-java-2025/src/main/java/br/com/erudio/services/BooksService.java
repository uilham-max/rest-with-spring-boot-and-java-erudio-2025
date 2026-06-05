package br.com.erudio.services;

import br.com.erudio.data.dto.v1.BooksDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BooksService {

    BooksDTO create(BooksDTO booksDTO);

    List<BooksDTO> getAll();

    BooksDTO findById(Long id);

    BooksDTO update(BooksDTO booksDTO);

    void delete(Long id);

}
