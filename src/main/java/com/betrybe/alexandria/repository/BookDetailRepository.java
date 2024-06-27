package com.betrybe.alexandria.repository;

import com.betrybe.alexandria.entity.BookDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDetailRepository extends JpaRepository<BookDetail, Long> {
}
