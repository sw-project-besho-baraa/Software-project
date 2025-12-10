package Service_Test.Fine_Test;

import Entity.MediaItem;
import Entity.User;
import Repository.MediaItemRepository;
import Repository.UserRepository;
import Service.Fine.CalculateFineService;
import Service.OverdueBorrowDetection.OverdueBorrowedItem;
import Service.OverdueBorrowDetection.OverdueBorrowedItemsData;
import Service.OverdueBorrowDetection.OverdueItemDetector;
import Util.DateCalculator.DateCalculator;
import Util.FineResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalculateFineService_Test
{

    @Mock
    MediaItemRepository mediaItemRepository;

    @Mock
    UserRepository userRepository;

    @Test
    void calculateFines_allBranches()
    {
        LocalDateTime now = LocalDateTime.of(2025,1,10,0,0);
        User borrower = mock(User.class);
        MediaItem item1 = mock(MediaItem.class);
        MediaItem item2 = mock(MediaItem.class);
        OverdueBorrowedItem oi1 = mock(OverdueBorrowedItem.class);
        OverdueBorrowedItem oi2 = mock(OverdueBorrowedItem.class);
        OverdueBorrowedItemsData data = mock(OverdueBorrowedItemsData.class);

        when(oi1.item()).thenReturn(item1);
        when(oi2.item()).thenReturn(item2);
        when(data.user()).thenReturn(borrower);
        when(data.items()).thenReturn(List.of(oi1,oi2));

        LocalDateTime due = now.minusDays(3);
        when(item1.getLastTimeFineCalculated()).thenReturn(null);
        when(item1.getDueDate()).thenReturn(due);
        when(item2.getLastTimeFineCalculated()).thenReturn(now);

        try (MockedConstruction<OverdueItemDetector> ctor = Mockito.mockConstruction(OverdueItemDetector.class,
                (m,c) -> when(m.detectUsersWithOverdueBooks()).thenReturn(List.of(data)));
                MockedStatic<DateCalculator> dc = Mockito.mockStatic(DateCalculator.class);
                MockedStatic<FineResolver> fr = Mockito.mockStatic(FineResolver.class))
        {

            dc.when(() -> DateCalculator.daysDifference(now,due)).thenReturn(3L);
            dc.when(() -> DateCalculator.daysDifference(now,now)).thenReturn(0L);
            fr.when(() -> FineResolver.getFine(item1)).thenReturn(new BigDecimal("2"));
            fr.when(() -> FineResolver.getFine(item2)).thenReturn(new BigDecimal("5"));

            CalculateFineService s = new CalculateFineService(mediaItemRepository, userRepository);
            s.calculateFines(now);
        }

        verify(borrower).increaseFine(new BigDecimal("6"));
        verify(item1).setLastTimeFineCalculated(now);
        verify(mediaItemRepository).save(item1);
        verify(userRepository).save(borrower);

        verify(item2,never()).setLastTimeFineCalculated(any());
        verify(mediaItemRepository,never()).save(item2);
    }

    @Test
    void calculateFines_emptyList()
    {
        LocalDateTime now = LocalDateTime.of(2025,1,10,0,0);

        try (MockedConstruction<OverdueItemDetector> ctor = Mockito.mockConstruction(OverdueItemDetector.class,
                (m,c) -> when(m.detectUsersWithOverdueBooks()).thenReturn(List.of())))
        {
            CalculateFineService s = new CalculateFineService(mediaItemRepository, userRepository);
            s.calculateFines(now);
        }

        verifyNoInteractions(mediaItemRepository,userRepository);
    }
}
