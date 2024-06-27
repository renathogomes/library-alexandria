package com.betrybe.alexandria.controller;

import com.betrybe.alexandria.controller.dto.PublisherCreationDto;
import com.betrybe.alexandria.controller.dto.PublisherDto;
import com.betrybe.alexandria.entity.Publisher;
import com.betrybe.alexandria.exception.PublisherNotFoundException;
import com.betrybe.alexandria.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {
    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/{id}")
    public PublisherDto getPublisherById(@PathVariable Long id) throws PublisherNotFoundException {
        return PublisherDto.fromEntity(publisherService.findById(id));
    }

    @GetMapping
    public List<PublisherDto> getAllPublishers() {
        List<Publisher> allPublishers = publisherService.findAll();

        return allPublishers.stream().map(PublisherDto::fromEntity).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PublisherDto createPublisher(@RequestBody PublisherCreationDto publisherCreationDto) {
        return PublisherDto.fromEntity(publisherService.create(publisherCreationDto.toEntity()));
    }

    @PutMapping("/{id}")
    public PublisherDto updatePublisher(@PathVariable Long id, @RequestBody PublisherCreationDto publisherCreationDto) throws PublisherNotFoundException {
        return PublisherDto.fromEntity(publisherService.update(id, publisherCreationDto.toEntity()));
    }

    @DeleteMapping("/{id}")
    public PublisherDto deletePublisher(@PathVariable Long id) throws PublisherNotFoundException {
        return PublisherDto.fromEntity(publisherService.delete(id));
    }
}
