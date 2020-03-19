package com.gidi.library.service;

import com.gidi.library.dto.BookDto;
import com.gidi.library.model.BookCollection;
import com.gidi.library.response.BookData;
import com.gidi.library.response.BookSearchResult;

import java.util.List;

public interface BookService {
    BookData addBook(BookDto bookDto);
    String deleteBookCollection(Long bookCollectionId);
    String deleteSingleBookCopy(Long bookId);
    BookCollection updateBook(BookDto book, Long bookId);
    List<BookSearchResult> searchLibrary(String searchTerm);
    BookData lendBookToUser(Long userId, Long bookId);
}
