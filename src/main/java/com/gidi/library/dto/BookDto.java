package com.gidi.library.dto;

import javax.validation.constraints.NotBlank;

public class BookDto {
    @NotBlank(message = "Please enter book title")
    private String title;

    @NotBlank(message = "Please enter the ISBN")
    private String isbn;

    @NotBlank(message = "Please enter author's name")
    private String author;

    @NotBlank(message = "Please enter publisher's information")
    private String publisher;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

}
