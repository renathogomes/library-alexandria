package com.betrybe.alexandria.exception;

public class PublisherNotFoundException extends NotFoundException{
    public PublisherNotFoundException() {
        super("Editora não encontrada!");
    }
}
