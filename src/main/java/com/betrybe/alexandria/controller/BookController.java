package com.betrybe.alexandria.controller;

import com.betrybe.alexandria.controller.dto.BookCreateDto;
import com.betrybe.alexandria.controller.dto.BookDetailCreationDto;
import com.betrybe.alexandria.controller.dto.BookDetailDto;
import com.betrybe.alexandria.controller.dto.BookDto;
import com.betrybe.alexandria.entity.Book;
import com.betrybe.alexandria.exception.AuthorNotFoundException;
import com.betrybe.alexandria.exception.BookDetailNotFoundException;
import com.betrybe.alexandria.exception.BookNotFoundException;
import com.betrybe.alexandria.exception.PublisherNotFoundException;
import com.betrybe.alexandria.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookServicee) {
        this.bookService = bookServicee;
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) throws BookNotFoundException {
        return BookDto.fromEntity(bookService.findById(id));
    }

    @GetMapping
    public List<BookDto> getAllBooks(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "20") int pageSize
    ) {
        List<Book> allBooks = bookService.findAll(pageNumber, pageSize);

        return allBooks.stream().map(BookDto::fromEntity).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@RequestBody BookCreateDto bookCreateDto) {
        return BookDto.fromEntity(bookService.create(bookCreateDto.toEntity()));
    }

    @PutMapping("{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody BookCreateDto bookCreateDto) throws BookNotFoundException {
        return BookDto.fromEntity(bookService.update(id, bookCreateDto.toEntity()));
    }

    @DeleteMapping("/{id}")
    public BookDto deleteBookById(@PathVariable Long id) throws BookNotFoundException {
        return BookDto.fromEntity(bookService.deleteById(id));
    }

    /**
     * Rotas para Detail
     */

    @PostMapping("/{bookId}/detail")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDetailDto createBookDetail(@PathVariable Long bookId, @RequestBody BookDetailCreationDto newBookDetailDto) throws BookNotFoundException {
        return BookDetailDto.fromEntity(bookService.createBookDetail(bookId, newBookDetailDto.toEntity()));
    }

    @GetMapping("/{bookId}/detail")
    public BookDetailDto getBookDetail(@PathVariable Long bookId) throws BookNotFoundException, BookDetailNotFoundException {
        return BookDetailDto.fromEntity(bookService.getBookDetail(bookId));
    }

    @PutMapping("/{bookId}/detail")
    public BookDetailDto updateBookDetail(@PathVariable Long bookId, @RequestBody BookDetailCreationDto newBookDetailDto) throws BookNotFoundException, BookDetailNotFoundException {
        return BookDetailDto.fromEntity(bookService.updateBookDetail(bookId, newBookDetailDto.toEntity()));
    }

    @DeleteMapping("/{bookId}/detail")
    public BookDetailDto removeBookDetail(@PathVariable Long bookId) throws BookDetailNotFoundException, BookNotFoundException {
        return BookDetailDto.fromEntity(bookService.removeBookDetail(bookId));
    }

    // atualizando ou deletando editora
    @PutMapping("/{bookId}/publisher/{publisherId}") // com essa rota ele consegue ligar o book com a editora
    public BookDto setBookdPublisher(@PathVariable Long bookId, @PathVariable Long publisherId) throws BookNotFoundException, PublisherNotFoundException {
        return BookDto.fromEntity(bookService.setBookPublisher(bookId, publisherId));
    }

    @DeleteMapping("/{bookId}/publisher")
    public BookDto removeBookPublisher(@PathVariable Long bookId) throws BookNotFoundException {
        return BookDto.fromEntity(bookService.removeBookPublisher(bookId));
    }

    // Rotas relacionadas ao N:N
    @PutMapping("/{bookId}/authors/{authorId}")
    public BookDto addBookAuthor(@PathVariable Long bookId, @PathVariable Long authorId) throws AuthorNotFoundException, BookNotFoundException {
        return BookDto.fromEntity(bookService.addBookAuthor(bookId, authorId));
    }

    @DeleteMapping("/{bookId}/authors/{authorId}")
    public BookDto removeBookAuthor(@PathVariable Long bookId, @PathVariable Long authorId) throws AuthorNotFoundException, BookNotFoundException {
        return BookDto.fromEntity(bookService.removeBookAuthor(bookId, authorId));
    }
}
