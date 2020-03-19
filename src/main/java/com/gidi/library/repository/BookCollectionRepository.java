package com.gidi.library.repository;

import com.gidi.library.model.BookCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookCollectionRepository extends JpaRepository<BookCollection, Long> {
    Optional<BookCollection> findByIsbn(String isbn);
    List<BookCollection> findDistinctByTitleContainingIgnoreCase(String searchTerm);
}
