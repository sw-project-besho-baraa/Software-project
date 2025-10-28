package org.Library.DAL.Repositories.Interfaces;

import org.Library.DAL.Models.BorrowedBook;

import java.time.LocalDate;
import java.util.List;

public interface IBorrowedBookRepository {
    public void borrowBook(String ISBN , String userName , LocalDate borrowDate);

    List<BorrowedBook> getOverdueBooks(LocalDate todayData);
}
