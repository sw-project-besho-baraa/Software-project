package BooksTests;
import org.Library.BLL.Services.Classes.OverdueService;
import org.Library.BLL.Services.Interfaces.IOverdueService;
import org.Library.DAL.Models.BorrowedBook;
import org.Library.DAL.Repositories.Classes.BorrowedBookRepository;
import org.Library.DAL.Repositories.Interfaces.IBorrowedBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class OverdueDetectionTests
{
    IBorrowedBookRepository borrowedBookRepo=new BorrowedBookRepository();
    IOverdueService overdueService=new OverdueService(borrowedBookRepo);
    @BeforeEach
    void setup(){
        borrowedBookRepo.borrowBook("122","besho",LocalDate.of(2022,12,1));
    }


    @Test
    void overdueGetAllOverDueBooksTest(){
        List<BorrowedBook>books=overdueService.getOverdueBooks(LocalDate.now());
        assertFalse(books.isEmpty());
    }

}
