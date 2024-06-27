package com.betrybe.alexandria.exception;

public class AuthorNotFoundException extends NotFoundException{
    public AuthorNotFoundException() {
        super("Autor não encontrado");
    }
}
