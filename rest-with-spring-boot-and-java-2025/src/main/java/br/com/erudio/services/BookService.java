package br.com.erudio.services;

import br.com.erudio.data.dto.v1.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {

    BookDTO create(BookDTO bookDTO);

    PagedModel<EntityModel<BookDTO>> findAll(Pageable pageable);

    BookDTO findById(Long id);

    BookDTO update(BookDTO bookDTO);

    void delete(Long id);

}
