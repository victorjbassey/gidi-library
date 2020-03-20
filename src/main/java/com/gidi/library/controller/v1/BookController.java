package com.gidi.library.controller.v1;

import com.gidi.library.dto.BookDto;
import com.gidi.library.model.BookCollection;
import com.gidi.library.response.BookData;
import com.gidi.library.response.BookSearchResult;
import com.gidi.library.response.ResponseTemplate;
import com.gidi.library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseTemplate<BookData> addNewBook(@Valid @RequestBody BookDto bookDto) {
        BookData bookData = bookService.addBook(bookDto);
        return new ResponseTemplate<>(HttpStatus.CREATED.value(), "Successfully added new book", bookData);
    }

    @PutMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseTemplate<BookCollection> updateBook(@Valid @RequestBody BookDto bookDto, @PathVariable Long bookId) {
        BookCollection updatedBook = bookService.updateBook(bookDto, bookId);
        return new ResponseTemplate<>(HttpStatus.OK.value(), "Successfully updated book with id - " + bookId, updatedBook);
    }

    @DeleteMapping("/{bookCollectionId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteBookCollection(@PathVariable Long bookCollectionId) {
        return bookService.deleteBookCollection(bookCollectionId);
    }

    @DeleteMapping("/copy/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteSingleBookCopy(@PathVariable Long bookId) {
        return bookService.deleteSingleBookCopy(bookId);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseTemplate<List<BookSearchResult>> search(@RequestParam String value) {
        List<BookSearchResult> result = bookService.searchLibrary(value);
        return new ResponseTemplate<>(HttpStatus.OK.value(), "Successfully retrieved books", result);
    }

    @GetMapping("/lend/{bookId}/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseTemplate<BookData> lendBook(@PathVariable Long bookId, @PathVariable Long userId) {
        BookData bookData = bookService.lendBookToUser(userId, bookId);
        return new ResponseTemplate<>(HttpStatus.OK.value(), "Successfully lent book to user with id - " + userId, bookData);
    }

}
