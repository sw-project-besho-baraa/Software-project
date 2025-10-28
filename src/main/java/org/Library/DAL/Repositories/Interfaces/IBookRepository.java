package org.Library.DAL.Repositories.Interfaces;

import org.Library.DAL.Models.Book;

import java.util.List;

public interface IBookRepository {
    void addBook(Book book);
    List<Book> search(String bookDetails);

    Book searchByISBN(String isbn);

    boolean isBookAvilable(String isbn);
    void setAvailable(String isbn, boolean available);
}
