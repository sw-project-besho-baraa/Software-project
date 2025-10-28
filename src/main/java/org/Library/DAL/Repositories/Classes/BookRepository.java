package org.Library.DAL.Repositories.Classes;

import org.Library.BLL.Services.Interfaces.IAuthenticationService;
import org.Library.DAL.Models.Book;
import org.Library.DAL.Repositories.Interfaces.IBookRepository;

import java.util.ArrayList;
import java.util.List;

import static org.Library.DAL.Data.Books.BooksList;

public class BookRepository implements IBookRepository {

    @Override
    public void addBook(Book book) {
        BooksList.add(book);
    }

    @Override
    public List<Book> search(String bookDetails) {
        if (bookDetails == null || bookDetails.equals("")){
            return BooksList;
        }
        else {
            List<Book> books = new ArrayList<>();
            for (Book book : BooksList) {
                if(book.getIsbn().contains(bookDetails)||book.getTitle().contains(bookDetails)||book.getAuthor().contains(bookDetails)) {
                    books.add(book);
                }

            }
            return books;
        }
    }

    @Override
    public Book searchByISBN(String isbn) {
        if(isbn==null || isbn.equals("")){
            return null;
        }
        for (Book book : BooksList) {
            if(book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;

    }

    @Override
    public boolean isBookAvilable(String isbn) {
        Book book=searchByISBN(isbn);
        return book.isAvailable();
    }

    @Override
    public void setAvailable(String isbn, boolean available) {
        Book book=searchByISBN(isbn);
        book.setAvailable(available);
    }


}
