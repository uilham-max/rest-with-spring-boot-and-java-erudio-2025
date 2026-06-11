package br.com.erudio.services;

import br.com.erudio.data.dto.v1.BookDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {

    BookDTO create(BookDTO bookDTO);

    List<BookDTO> findAll();

    BookDTO findById(Long id);

    BookDTO update(BookDTO bookDTO);

    void delete(Long id);

}
