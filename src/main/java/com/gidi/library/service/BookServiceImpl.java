package com.gidi.library.service;

import com.gidi.library.dto.BookDto;
import com.gidi.library.exception.CurrentlyNotAvailableException;
import com.gidi.library.exception.ResourceNotFoundException;
import com.gidi.library.model.Book;
import com.gidi.library.model.BookCollection;
import com.gidi.library.model.User;
import com.gidi.library.repository.BookCollectionRepository;
import com.gidi.library.repository.BookRepository;
import com.gidi.library.repository.UserRepository;
import com.gidi.library.response.BookData;
import com.gidi.library.response.BookSearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private BookCollectionRepository bookCollectionRepository;

    public BookServiceImpl(BookRepository bookRepository, UserRepository userRepository,
                           BookCollectionRepository bookCollectionRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.bookCollectionRepository = bookCollectionRepository;
    }

    @Override
    public BookData addBook(BookDto bookDto) {
        BookCollection bookCollection = bookCollectionRepository.findByIsbn(bookDto.getIsbn()).orElse(null);
        if (bookCollection == null) {
            bookCollection = getBookCollectionFromDto(bookDto);
        }
        Book newBook = new Book();
        newBook.setBookCollection(bookCollection);
        Book addedBook = bookRepository.save(newBook);
        logger.info("Book with id - " + addedBook.getId() + " successfully added");
        return new BookData(addedBook.getId(), bookCollection.getTitle(), bookCollection.getAuthor(), bookCollection.getPublisher(),
                bookCollection.getIsbn(), addedBook.getAddedAt(), bookCollection.getUpdatedAt());
    }

    @Override
    public String deleteBookCollection(Long bookCollectionId) {
        Optional<BookCollection> bookCollectionOptional = bookCollectionRepository.findById(bookCollectionId);
        if (bookCollectionOptional.isEmpty()) {
            throw new ResourceNotFoundException("Book with collection id - " + bookCollectionId + " does not exist");
        }
        bookCollectionRepository.delete(bookCollectionOptional.get());
        logger.info("Book with collection id - " + bookCollectionId + " deleted");
        return "Successfully deleted book with collection id - " + bookCollectionId;
    }

    @Override
    public String deleteSingleBookCopy(Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty()) throw new ResourceNotFoundException("Book with id - " + bookId + " does not exist");
        bookRepository.delete(bookOptional.get());
        logger.info("Book with id - " + bookId + " deleted");
        return "Successfully deleted book with id - " + bookId;
    }

    @Override
    public BookCollection updateBook(BookDto bookDto, Long bookCollectionId) {
        BookCollection bookCollection = getBookCollectionFromDto(bookDto);
        Optional<BookCollection> bookOptional = bookCollectionRepository.findById(bookCollectionId);
        if (bookOptional.isPresent()) {
            BookCollection theBookCollection = bookOptional.get();
            bookCollection.setId(theBookCollection.getId());
        } else {
            throw new ResourceNotFoundException("Book with collection id - " + bookCollectionId + " does not exist");
        }
        logger.info("Book with collection id - " + bookCollectionId + " successfully updated");
        return bookCollectionRepository.save(bookCollection);
    }

    private BookCollection getBookCollectionFromDto(BookDto bookDto) {
        return new BookCollection(bookDto.getTitle(), bookDto.getIsbn(), bookDto.getAuthor(), bookDto.getPublisher());
    }

    @Override
    public List<BookSearchResult> searchLibrary(String searchTerm) {
        List<BookCollection> searchOutput = bookCollectionRepository.findDistinctByTitleContainingIgnoreCase(searchTerm);
        List<BookSearchResult> searchResults = new ArrayList<>();
        for (BookCollection output : searchOutput) {
            BookSearchResult result = new BookSearchResult(output);
            result.setTotalCopies(output.getCopies().size());
            result.setAvailableCopies((int) output.getCopies().stream()
                    .filter(copy -> copy.getUserInPossession() == null).count());
            searchResults.add(result);
        }
        return searchResults;
    }

    @Override
    public BookData lendBookToUser(Long userId, Long bookCollectionId) {
        User theUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id - " + userId + " does not exist"));
        BookCollection theBookCollection = bookCollectionRepository.findById(bookCollectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Book with collection id - " + bookCollectionId + " does not exist"));
        List<Book> bookCopies = theBookCollection.getCopies();
        Optional<Book> bookOptional = bookCopies.stream().filter(copy -> copy.getUserInPossession() == null).findFirst();

        if (bookOptional.isEmpty()) {
            throw new CurrentlyNotAvailableException("Sorry. The book is currently not available");
        }
        Book theBook = bookOptional.get();
        theBook.setUserInPossession(theUser);
        logger.info("Book with id - " + theBook.getId() + " successfully lent to user with id - " + userId);
        Book lentBook = bookRepository.save(theBook);
        BookData bookData = new BookData(lentBook.getId(), theBookCollection.getTitle(), theBookCollection.getAuthor(),
                theBookCollection.getPublisher(), theBookCollection.getIsbn(),
                lentBook.getAddedAt(), theBookCollection.getUpdatedAt());
        bookData.setIdOfUserInPossession(lentBook.getUserInPossession().getId());
        return bookData;
    }
}
