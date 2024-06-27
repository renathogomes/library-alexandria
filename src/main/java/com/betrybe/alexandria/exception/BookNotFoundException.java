package com.betrybe.alexandria.exception;

public class BookNotFoundException extends NotFoundException{
    public BookNotFoundException() {
        super("Livro não encontrado!");
    }
}
