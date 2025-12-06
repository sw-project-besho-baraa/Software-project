package Service_Test.BookService_Test.OverdueBorrowDetection_Test;

import DTO.UserDTO.UserContactDTO;
import Entity.MediaItem;
import Repository.UserRepository;
import Service.MediaItem.OverdueBorrowDetection.OverdueItemDetector;
import Service.MediaItem.OverdueBorrowDetection.OverdueBorrowedItem;
import Service.MediaItem.OverdueBorrowDetection.OverdueBorrowedItemsData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OverdueBookDetector_Test
{

    private UserRepository userRepository;
    private OverdueItemDetector overdueBookDetector;
    private final int LIMIT = 7;

    @BeforeEach
    void setup()
    {
        userRepository = mock(UserRepository.class);
        overdueBookDetector = new OverdueItemDetector(LIMIT, userRepository)
        {
        };
    }

    @Test
    void detectUsersWithOverdueBooks_ValidCall_CallsRepositoryAndReturnsResult()
    {
        MediaItem itemMock = mock(MediaItem.class);
        UserContactDTO userContactMock = mock(UserContactDTO.class);
        OverdueBorrowedItem overdueItem = new OverdueBorrowedItem(itemMock, 3, LocalDate.now());
        OverdueBorrowedItemsData data = new OverdueBorrowedItemsData(userContactMock, List.of(overdueItem));
        List<OverdueBorrowedItemsData> expected = List.of(data);
        when(userRepository.findUsersWithBookingsExceedingDuration(LIMIT)).thenReturn(expected);
        List<OverdueBorrowedItemsData> result = overdueBookDetector.detectUsersWithOverdueBooks();
        assertNotNull(result);
        assertEquals(1,result.size());
        assertEquals(expected,result);
        verify(userRepository,times(1)).findUsersWithBookingsExceedingDuration(LIMIT);
    }

    @Test
    void detectUsersWithOverdueBooks_EmptyResult_ReturnsEmptyList()
    {
        when(userRepository.findUsersWithBookingsExceedingDuration(LIMIT)).thenReturn(List.of());
        List<OverdueBorrowedItemsData> result = overdueBookDetector.detectUsersWithOverdueBooks();
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository,times(1)).findUsersWithBookingsExceedingDuration(LIMIT);
    }
}
