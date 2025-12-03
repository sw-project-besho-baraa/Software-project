package Service.BookService.OverdueBorrowDetection_Test;

import Entity.Item;
import Entity.User;
import Service.Book.OverdueBorrowDetection.OverdueBorrowData;
import Service.Book.OverdueBorrowDetection.OverdueListener;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OverdueListener_Test
{

    @Test
    void update_PrintsEventMessageToConsole()
    {
        OverdueListener listener = new OverdueListener();
        User user = mock(User.class);
        Item item = mock(Item.class);
        when(item.getTitle()).thenReturn("Refactoring");
        OverdueBorrowData event = new OverdueBorrowData(user, item, 2, LocalDate.now());
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        try
        {
            listener.update("overdue",event);
            String output = outContent.toString().trim();
            assertEquals("Item \"Refactoring\" is overdue by 2 days",output);
        } finally
        {
            System.setOut(originalOut);
        }
    }
}
