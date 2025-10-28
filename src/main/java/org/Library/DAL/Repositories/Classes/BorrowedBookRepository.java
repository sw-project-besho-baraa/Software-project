package org.Library.DAL.Repositories.Classes;

import org.Library.DAL.Models.BorrowedBook;
import org.Library.DAL.Repositories.Interfaces.IBorrowedBookRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.Library.DAL.Data.BorrowedBooks.BorrowedBooksList;

public class BorrowedBookRepository implements IBorrowedBookRepository {

    @Override
    public void borrowBook(String ISBN, String userName, LocalDate borrowDate) {
        BorrowedBooksList.add(new BorrowedBook(ISBN, userName, borrowDate));
    }

    @Override
    public List<BorrowedBook> getOverdueBooks(LocalDate todayData) {
        List<BorrowedBook> overdueBooks = new ArrayList<>();
        for (BorrowedBook book : BorrowedBooksList) {
            if(book.isOverdue()){
                overdueBooks.add(book);
            }
        }
        return overdueBooks;
    }
}
