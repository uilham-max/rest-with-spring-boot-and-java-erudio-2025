package br.com.erudio.unittests.services.impl;

import br.com.erudio.data.dto.v1.BookDTO;
import br.com.erudio.exception.RequiredObjectIsNullException;
import br.com.erudio.model.Book;
import br.com.erudio.repository.BookRepository;
import br.com.erudio.services.impl.BookServiceImpl;
import br.com.erudio.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    MockBook input;

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Book book = input.mockEntity(1);
        book.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        var result = bookService.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Title Test1", result.getTitle());
        assertEquals("Author Test1", result.getAuthor());
        assertEquals(new Timestamp(1), result.getLaunchDate());
        assertEquals(new BigDecimal(1), result.getPrice());
        assertNotNull(result.getLinks());
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                && link.getHref().endsWith("api/book/v1/1")
                && link.getType().equals("GET")
                )
        );
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("api/book/v1/")
                        && link.getType().equals("GET")
                )
        );
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("api/book/v1/")
                        && link.getType().equals("POST")
                )
        );
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("api/book/v1/")
                        && link.getType().equals("PUT")
                )
        );
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("api/book/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

    }

    @Test
    void create() {
        Book book = input.mockEntity(1);
        Book persisted = book;
        BookDTO dto = input.mockDTO(1);
        when(bookRepository.save(book)).thenReturn(persisted);

        var result = bookService.create(dto);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Title Test1", result.getTitle());
        assertEquals("Author Test1", result.getAuthor());
        assertEquals(new Timestamp(1), result.getLaunchDate());
        assertEquals(new BigDecimal(1), result.getPrice());
        assertNotNull(result.getLinks());
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("api/book/v1/1")
                        && link.getType().equals("GET")
                )
        );
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("api/book/v1/")
                        && link.getType().equals("GET")
                )
        );
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("api/book/v1/")
                        && link.getType().equals("POST")
                )
        );
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("api/book/v1/")
                        && link.getType().equals("PUT")
                )
        );
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("api/book/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

    }

    @Test
    void testCreateWithNullBook(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
            bookService.create(null);
        });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void update() {
        Book book = input.mockEntity(1);
        Book persisted = book;
        BookDTO dto = input.mockDTO(1);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(persisted);

        var result = bookService.update(dto);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Title Test1", result.getTitle());
        assertEquals("Author Test1", result.getAuthor());
        assertEquals(new Timestamp(1), result.getLaunchDate());
        assertEquals(new BigDecimal(1), result.getPrice());
        assertNotNull(result.getLinks());
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("api/book/v1/1")
                        && link.getType().equals("GET")
                )
        );
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("api/book/v1/")
                        && link.getType().equals("GET")
                )
        );
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("api/book/v1/")
                        && link.getType().equals("POST")
                )
        );
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("api/book/v1/")
                        && link.getType().equals("PUT")
                )
        );
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("api/book/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

    }

    @Test
    void testUpdateWithNullBook(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    bookService.update(null);
                });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void delete() {
        Book book = input.mockEntity(1);
        book.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        bookService.delete(1L);
        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, times(1)).delete(any(Book.class));
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @Disabled("REASON: Still Under Development.")
    void findAll() {
        List<Book> listBook = input.mockEntityList();
        when(bookRepository.findAll()).thenReturn(listBook);
        List<BookDTO> listBookDto = new ArrayList<>(); //bookService.findAll();
        assertNotNull(listBookDto);
        assertEquals(listBook.size(), listBookDto.size());

        BookDTO bookOne = listBookDto.get(1);

        assertNotNull(bookOne);
        assertNotNull(bookOne.getId());
        assertEquals("Title Test1", bookOne.getTitle());
        assertEquals("Author Test1", bookOne.getAuthor());
        assertEquals(new Timestamp(1), bookOne.getLaunchDate());
        assertEquals(new BigDecimal(1), bookOne.getPrice());
        assertNotNull(bookOne.getLinks());
        assertNotNull(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("api/book/v1/1")
                        && link.getType().equals("GET")
                )
        );
        assertNotNull(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("api/book/v1/")
                        && link.getType().equals("GET")
                )
        );
        assertNotNull(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("api/book/v1/")
                        && link.getType().equals("POST")
                )
        );
        assertNotNull(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("api/book/v1/")
                        && link.getType().equals("PUT")
                )
        );
        assertNotNull(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("api/book/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        BookDTO bookFour = listBookDto.get(4);

        assertNotNull(bookFour);
        assertNotNull(bookFour.getId());
        assertEquals("Title Test4", bookFour.getTitle());
        assertEquals("Author Test4", bookFour.getAuthor());
        assertEquals(new Timestamp(4), bookFour.getLaunchDate());
        assertEquals(new BigDecimal(4), bookFour.getPrice());
        assertNotNull(bookFour.getLinks());
        assertNotNull(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("api/book/v1/4")
                        && link.getType().equals("GET")
                )
        );
        assertNotNull(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("api/book/v1/")
                        && link.getType().equals("GET")
                )
        );
        assertNotNull(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("api/book/v1/")
                        && link.getType().equals("POST")
                )
        );
        assertNotNull(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("api/book/v1/")
                        && link.getType().equals("PUT")
                )
        );
        assertNotNull(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("api/book/v1/4")
                        && link.getType().equals("DELETE")
                )
        );

        BookDTO bookSeven = listBookDto.get(7);

        assertNotNull(bookSeven);
        assertNotNull(bookSeven.getId());
        assertEquals("Title Test7", bookSeven.getTitle());
        assertEquals("Author Test7", bookSeven.getAuthor());
        assertEquals(new Timestamp(7), bookSeven.getLaunchDate());
        assertEquals(new BigDecimal(7), bookSeven.getPrice());
        assertNotNull(bookSeven.getLinks());
        assertNotNull(bookSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("api/book/v1/7")
                        && link.getType().equals("GET")
                )
        );
        assertNotNull(bookSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("api/book/v1/")
                        && link.getType().equals("GET")
                )
        );
        assertNotNull(bookSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("api/book/v1/")
                        && link.getType().equals("POST")
                )
        );
        assertNotNull(bookSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("api/book/v1/")
                        && link.getType().equals("PUT")
                )
        );
        assertNotNull(bookSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("api/book/v1/7")
                        && link.getType().equals("DELETE")
                )
        );

    }
}