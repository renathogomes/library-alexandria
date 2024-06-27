package com.betrybe.alexandria;

import com.betrybe.alexandria.entity.Book;
import com.betrybe.alexandria.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;

import java.util.ArrayList;
import java.util.List;

// Classe de inicialização, não entendi muito a utilidade mas vou implementar pois pode ser util um dia

public class DatabaseSeeder implements CommandLineRunner {
    private final BookRepository bookRepository;

    public DatabaseSeeder(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {

    }

    private void seedBooks() {
        List<Book> books = new ArrayList<>();

        books.add(new Book("The Fall of the Roman Republic", "History"));
        books.add(new Book("The Civil War", "History"));
    }
}
