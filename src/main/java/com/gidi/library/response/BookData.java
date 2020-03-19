package com.gidi.library.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookData {
    private long bookId;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private long idOfUserInPossession;
    private LocalDateTime addedAt;
    private LocalDateTime updatedAt;

    public BookData() {
    }

    public BookData(long bookId, String title, String author, String publisher,
                    String isbn, LocalDateTime addedAt, LocalDateTime updatedAt) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.addedAt = addedAt;
        this.updatedAt = updatedAt;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public long getIdOfUserInPossession() {
        return idOfUserInPossession;
    }

    public void setIdOfUserInPossession(long idOfUserInPossession) {
        this.idOfUserInPossession = idOfUserInPossession;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
