package org.Library.BLL.Services.Interfaces;

import org.Library.DAL.Models.Book;

import java.util.List;

public interface IBookService {
    public boolean addBook(Book book);
    public List<Book> searchForBooks(String bookDetails);
    Book searchForBooksByISBN(String isbn);
    boolean isBookAvilable(String isbn);
    void setAvailable(String isbn, boolean b);
}
