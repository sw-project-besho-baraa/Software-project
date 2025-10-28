package org.Library.BLL.Services.Classes;

import org.Library.BLL.Services.Interfaces.IOverdueService;
import org.Library.DAL.Models.BorrowedBook;
import org.Library.DAL.Repositories.Classes.BorrowedBookRepository;
import org.Library.DAL.Repositories.Interfaces.IBorrowedBookRepository;

import java.time.LocalDate;
import java.util.List;

public class OverdueService implements IOverdueService {
    IBorrowedBookRepository borrowedBookRepository ;
    public OverdueService(IBorrowedBookRepository borrowedBookRepository) {
        this.borrowedBookRepository = borrowedBookRepository;
    }

    @Override
    public List<BorrowedBook> getOverdueBooks(LocalDate todayData) {
        return borrowedBookRepository.getOverdueBooks(todayData);
    }
}
