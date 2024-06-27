package com.betrybe.alexandria.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity // indica que a classe é também uma entidade do banco de dados, criando o vínculo entre objetos da classe anotada e os registros da tabela no banco de dados;
@Table(name = "books") //indica qual é a tabela no banco de dados que estará associada à classe;
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // indica qual será a estratégia utilizada para geração dos valores da chave primária.
    // A opção GenerationType.IDENTITY significa: valores incrementados automaticamente, conforme o tipo do banco de dados. No MySQL isso quer dizer que a chave será do tipo AUTOINCREMENT
    private Long id;
    private String title;
    private String genre;
    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL ) // nome escolhido na BookDetail (book)
    private BookDetail details;

    @ManyToOne // muitos libros para uma editora
    @JoinColumn(name = "publisher_id")  // nome da coluna (chave extrangeira)
    private Publisher publisher;

    @ManyToMany // um livro pode ter muitos autores, como um autor pode escrever muitos livros
    @JoinTable(
            name = "authors_books",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    // criação da tabela intermediária, e definindo suas ligações por chaves primárias
    private List<Author> authors = new ArrayList<>();


    public Book() {
    }

    public Book(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }

    public BookDetail getDetails() {
        return details;
    }

    public void setDetails(BookDetail details) {
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}
