package org.Library.DAL.Models;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private boolean available = true;
    public Book(String isbn, String title, String author){
        this.isbn = isbn; this.title = title; this.author = author;
    }
    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
    public boolean isAvailable() {
        return available;

    }
    public void setAvailable(boolean available) {
        this.available = available;
    }

}
