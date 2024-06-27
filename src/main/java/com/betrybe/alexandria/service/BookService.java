package com.betrybe.alexandria.service;

import com.betrybe.alexandria.entity.Author;
import com.betrybe.alexandria.entity.Book;
import com.betrybe.alexandria.entity.BookDetail;
import com.betrybe.alexandria.entity.Publisher;
import com.betrybe.alexandria.exception.AuthorNotFoundException;
import com.betrybe.alexandria.exception.BookDetailNotFoundException;
import com.betrybe.alexandria.exception.BookNotFoundException;
import com.betrybe.alexandria.exception.PublisherNotFoundException;
import com.betrybe.alexandria.repository.BookDetailRepository;
import com.betrybe.alexandria.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookDetailRepository bookDetailRepository;
    private final PublisherService publisherService; // injecao de dependencia da editora para relacionamento 1:N
    private final AuthorService authorService; // injecao de dependencia do autor para fazer ligacao n:n

    //contrutores
    @Autowired
    public BookService(BookRepository bookRepository, BookDetailRepository bookDetailRepository, PublisherService publisherService, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.bookDetailRepository = bookDetailRepository;
        this.publisherService = publisherService;
        this.authorService = authorService;
    }

    // metodos book
    public Book findById(Long id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    public List<Book> findAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Book> page = bookRepository.findAll(pageable);
        return page.toList();
    }

    public Book create(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Long id, Book book) throws BookNotFoundException {
        Book bookFromDb = findById(id); // verifica se existe um livro pelo id, se nao tiver lanca um erro

        bookFromDb.setTitle(book.getTitle());
        bookFromDb.setGenre(book.getGenre());

        return bookRepository.save(bookFromDb);
    }

    public Book deleteById(Long id) throws BookNotFoundException {
        Book book = findById(id); // pega a entidade antes de apagar, pra saber se existe mesmo, e para retornar

        bookRepository.deleteById(id);

        return book;
    }

    // métodos do BookDetail

    public BookDetail createBookDetail(Long bookId, BookDetail bookDetail) throws BookNotFoundException {
        Book book = findById(bookId);

        bookDetail.setBook(book);

        return bookDetailRepository.save(bookDetail);
    }

    public BookDetail getBookDetail(Long bookId) throws BookNotFoundException, BookDetailNotFoundException {
        Book book = findById(bookId);

        BookDetail bookDetail = book.getDetails();

        if (bookDetail == null) {
            throw new BookDetailNotFoundException();
        }

        return bookDetail;
    }

    public BookDetail updateBookDetail(Long bookId, BookDetail bookDetail) throws BookNotFoundException, BookDetailNotFoundException {
        BookDetail bookDetailFromDb = getBookDetail(bookId);

        bookDetailFromDb.setSummary(bookDetail.getSummary());
        bookDetailFromDb.setPageCount(bookDetail.getPageCount());
        bookDetailFromDb.setYear(bookDetail.getYear());
        bookDetailFromDb.setIsbn(bookDetail.getIsbn());

        return bookDetailRepository.save(bookDetailFromDb);
    }

    public BookDetail removeBookDetail(Long bookId) throws BookNotFoundException, BookDetailNotFoundException {
        Book book = findById(bookId);
        BookDetail bookDetail = book.getDetails();

        if (bookDetail == null) {
            throw new BookDetailNotFoundException();
        }

        book.setDetails(null); // corta a ligação antes de remover
        bookDetail.setBook(null); // corta a ligação antes de remover

        bookDetailRepository.delete(bookDetail);

        return bookDetail;
    }

    // Metodos referentes ao relacionamento 1:N com a editora

    // bookId significa que o book deve existir, publisherId o publisher (editora) também deve existir. então basta receber os ids de cada uma delas
    public Book setBookPublisher(Long bookId, Long publisherId) throws BookNotFoundException, PublisherNotFoundException {
        Book book = findById(bookId); // encontrar primeiro o livro
        Publisher publisher = publisherService.findById(publisherId); // encontrar o publisher, mas ao invés de usar o repository, chama o service do publisher porque essa camada é do book e o método que chama o publisher Id esta no service do Publisher

        book.setPublisher(publisher);

        return bookRepository.save(book);
    }

    public Book removeBookPublisher(Long bookId) throws BookNotFoundException {
        Book book = findById(bookId);

        book.setPublisher(null); // quebra o relaciomanento, define no livro a editora como null
        // alem disso aqui esta trabalhando apenas com o Book, no caso bookDetail  ele estava usando as entidades book e bookDetail, ai precisou quebrar nos dois lados

        return bookRepository.save(book);
    }

    // Metodos referentes ao relacionamento N:N com Author

    public Book addBookAuthor(Long bookId, Long authorId) throws BookNotFoundException, AuthorNotFoundException {
        Book book = findById(bookId); // verifica se tem book
        Author author = authorService.findById(authorId); // verifica se tem author

        book.getAuthors().add(author); // depois de confirmar que os livros e autores existem, adciona o author

        return bookRepository.save(book); // agora salva o book com seu author ou sua lista de autores
    }

    public Book removeBookAuthor(Long bookId, Long authorId) throws BookNotFoundException, AuthorNotFoundException {
        Book book = findById(bookId);
        Author author = authorService.findById(authorId);

        book.getAuthors().remove(author);

        return bookRepository.save(book);
    }
}
