package com.gidi.library.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gidi.library.dto.BookDto;
import com.gidi.library.model.BookCollection;
import com.gidi.library.response.BookData;
import com.gidi.library.response.BookSearchResult;
import com.gidi.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    BookService bookService;

    @InjectMocks
    BookController bookController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void addNewBook() throws Exception {
        BookData bookData = new BookData(1L, "The Testaments", "M. Atwood",
                "A & B", "0345676828", null, null);
        when(bookService.addBook(any())).thenReturn(bookData);
        mockMvc.perform(post("/api/v1/books")
                .content(asJsonString(bookData))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.title").exists());
    }

    @Test
    void updateBook() throws Exception {
        BookCollection bookCollection = new BookCollection();
        BookDto bookDto = new BookDto();
        bookDto.setTitle("The mistress");
        bookDto.setIsbn("0987564732");
        bookDto.setPublisher("Amaze");
        bookDto.setAuthor("A. Apta");
        when(bookService.updateBook(any(), anyLong())).thenReturn(bookCollection);
        mockMvc.perform(put("/api/v1/books/{bookId}", 1)
                .content(asJsonString(bookDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Successfully updated book with id - 1"));
    }

    @Test
    void deleteBookCollection() throws Exception {
        when(bookService.deleteBookCollection(anyLong())).thenReturn("");
        mockMvc.perform(delete("/api/v1/books/{bookCollectionId}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void deleteSingleBookCopy() throws Exception {
        when(bookService.deleteSingleBookCopy(anyLong())).thenReturn("");
        mockMvc.perform(delete("/api/v1/books/copy/{bookId}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void search() throws Exception {
        List<BookSearchResult> searchResults = new ArrayList<>();
        searchResults.add(new BookSearchResult(new BookCollection()));
        when(bookService.searchLibrary(anyString())).thenReturn(searchResults);

        mockMvc.perform(get("/api/v1/books/search?value=the"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Successfully retrieved books"))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void lendBook() throws Exception {
        BookData bookData = new BookData();
        when(bookService.lendBookToUser(anyLong(), anyLong())).thenReturn(bookData);

        mockMvc.perform(get("/api/v1/books/lend/{bookId}/{userId}", 1, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Successfully lent book to user with id - 1"))
                .andExpect(jsonPath("$.data").exists());
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}