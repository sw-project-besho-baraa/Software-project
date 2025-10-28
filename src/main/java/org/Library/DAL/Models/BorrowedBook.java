package org.Library.DAL.Models;

import java.time.LocalDate;

public class BorrowedBook {
    private String userName;
    private String isbn;
    private LocalDate borrowedDate;
    private LocalDate returnedDate;
    public BorrowedBook(String userName, String isbn, LocalDate borrowedDate) {
        this.userName = userName;
        this.isbn = isbn;
        this.borrowedDate = borrowedDate;
        this.returnedDate = borrowedDate.plusDays(28);
    }


    public boolean isOverdue() {
        return LocalDate.now().isAfter(returnedDate.plusDays(1));
    }
}
