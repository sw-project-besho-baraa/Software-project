package org.Library.BLL.Services.Interfaces;

public interface IBorrowService {

    boolean borrowBook(String ISBN, String userName);
    String getErrorMassage();
}
