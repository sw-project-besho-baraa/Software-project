package Service_Test.MediaItem_Test;

import Repository.MediaItemRepository;
import Service.MediaItem.OverDueCountService;
import Util.CurrentLocalTimeDateResolver.CurrentLocalDateTimeResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OverDueCountService_Test
{

    @Test
    void countOverdueItems_usesCurrentTimeAndDelegatesToRepository()
    {
        MediaItemRepository repo = mock(MediaItemRepository.class);
        OverDueCountService service = new OverDueCountService(repo);

        LocalDateTime fakeNow = LocalDateTime.of(2025,12,10,18,0);

        try (MockedConstruction<CurrentLocalDateTimeResolver> mocked = mockConstruction(
                CurrentLocalDateTimeResolver.class,
                (mock,context) -> when(mock.getCurrentLocalDateTime()).thenReturn(fakeNow)))
        {

            when(repo.countByBorrowedTrueAndDueDateBefore(fakeNow)).thenReturn(3L);

            long result = service.countOverdueItems();

            assertEquals(3L,result);
            verify(repo).countByBorrowedTrueAndDueDateBefore(fakeNow);
        }
    }

    @Test
    void countOverdueItems_withGivenDate_delegatesToRepository()
    {
        MediaItemRepository repo = mock(MediaItemRepository.class);
        OverDueCountService service = new OverDueCountService(repo);

        LocalDateTime testTime = LocalDateTime.of(2025,12,11,10,30);
        when(repo.countByBorrowedTrueAndDueDateBefore(testTime)).thenReturn(5L);

        long result = service.countOverdueItems(testTime);

        assertEquals(5L,result);
        verify(repo).countByBorrowedTrueAndDueDateBefore(testTime);
    }
}