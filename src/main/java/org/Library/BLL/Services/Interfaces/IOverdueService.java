package org.Library.BLL.Services.Interfaces;

import org.Library.DAL.Models.BorrowedBook;

import java.time.LocalDate;
import java.util.List;

public interface IOverdueService {
    public List<BorrowedBook> getOverdueBooks(LocalDate todayData);
}
