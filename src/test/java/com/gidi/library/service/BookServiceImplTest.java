package com.gidi.library.service;

import com.gidi.library.dto.BookDto;
import com.gidi.library.exception.ResourceNotFoundException;
import com.gidi.library.model.Book;
import com.gidi.library.model.BookCollection;
import com.gidi.library.model.User;
import com.gidi.library.repository.BookCollectionRepository;
import com.gidi.library.repository.BookRepository;
import com.gidi.library.repository.UserRepository;
import com.gidi.library.response.BookData;
import com.gidi.library.response.BookSearchResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    BookRepository bookRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    BookCollectionRepository bookCollectionRepository;

    @InjectMocks
    BookServiceImpl bookService;

    @Test
    void addNewBookToCollection() {
        BookDto bookDto = getBookDto();
        Book newBook = getBook();
        BookCollection bookCollection = getBookCollectionFromDto(bookDto);
        newBook.setBookCollection(bookCollection);

        when(bookCollectionRepository.findByIsbn(anyString())).thenReturn(Optional.of(bookCollection));
        when(bookRepository.save(any())).thenReturn(newBook);

        BookData newBookData = bookService.addBook(bookDto);

        assertEquals(newBookData.getBookId(), newBook.getId());
        assertEquals(newBookData.getTitle(), bookCollection.getTitle());
    }

    @Test
    void deleteSingleBookFromCollection() {
        Book theBook = getBook();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(theBook));
        bookService.deleteSingleBookCopy(1L);
        verify(bookRepository).delete(theBook);
    }

    @Test
    void deleteBookCollectionWithAllCopies() {
        BookCollection bookCollection = getBookCollectionFromDto(getBookDto());
        when(bookCollectionRepository.findById(anyLong())).thenReturn(Optional.of(bookCollection));
        bookService.deleteBookCollection(1L);
        verify(bookCollectionRepository).delete(bookCollection);
    }

    @Test
    void deleteBookWithNonExistingId() {
        when(bookRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> bookService.deleteSingleBookCopy(1L));
    }

    @Test
    void updateBook() {
        BookCollection bookCollection = getBookCollectionFromDto(getBookDto());

        when(bookCollectionRepository.findById(anyLong())).thenReturn(Optional.of(bookCollection));
        when(bookCollectionRepository.save(any())).thenReturn(bookCollection);

        BookCollection updatedBookCollection = bookService.updateBook(getBookDto(), 1L);
        assertEquals(updatedBookCollection.getId(), bookCollection.getId());
    }

    @Test
    void searchLibrary() {
        List<BookCollection> books = new ArrayList<>();
        books.add(getBookCollectionFromDto(getBookDto()));

        when(bookCollectionRepository.findDistinctByTitleContainingIgnoreCase(anyString())).thenReturn(books);

        List<BookSearchResult> searchResult = bookService.searchLibrary("the");
        assertEquals(1, searchResult.size());
    }

    @Test
    void lendBookToUser() {
        User theUser = new User();
        theUser.setId(1L);
        Book theBook = getBook();
        BookCollection bookCollection = getBookCollectionFromDto(getBookDto());
        bookCollection.getCopies().add(theBook);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(theUser));
        when(bookCollectionRepository.findById(anyLong())).thenReturn(Optional.of(bookCollection));
        when(bookRepository.save(any())).thenReturn(theBook);

        BookData bookData = bookService.lendBookToUser(1L, 1L);
        assertEquals(theUser.getId(), bookData.getIdOfUserInPossession());
    }

    private BookCollection getBookCollectionFromDto(BookDto bookDto) {
        BookCollection collection = new BookCollection(bookDto.getTitle(),
                bookDto.getIsbn(), bookDto.getAuthor(), bookDto.getPublisher());
        collection.setId(1L);
        collection.setCopies(new ArrayList<>());
        return collection;
    }

    private BookDto getBookDto() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("The testaments");
        bookDto.setIsbn("0385543786");
        bookDto.setAuthor("M. Atwoood");
        bookDto.setPublisher("Nan A. Talese");
        return bookDto;
    }

    private Book getBook() {
        Book newBook = new Book();
        newBook.setId(1L);
        return newBook;
    }
}