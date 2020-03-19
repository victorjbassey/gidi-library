package com.gidi.library.response;

import com.gidi.library.model.BookCollection;

public class BookSearchResult {
    private BookCollection bookCollection;
    private long availableCopies;
    private long totalCopies;

    public BookSearchResult(BookCollection bookCollection) {
        this.bookCollection = bookCollection;
    }

    public BookCollection getBook() {
        return bookCollection;
    }

    public void setBooks(BookCollection book) {
        this.bookCollection = book;
    }

    public long getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public long getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }
}
