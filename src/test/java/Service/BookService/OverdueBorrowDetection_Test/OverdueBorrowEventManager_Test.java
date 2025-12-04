package Service.BookService.OverdueBorrowDetection_Test;

import Entity.Item;
import Entity.User;
import Service.Book.OverdueBorrowDetection.OverdueBarrowEventListener;
import Service.Book.OverdueBorrowDetection.OverdueBorrowData;
import Service.Book.OverdueBorrowDetection.OverdueBorrowEventManager;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

public class OverdueBorrowEventManager_Test
{

    @Test
    void notify_WithSubscribedListener_CallsUpdate()
    {
        OverdueBorrowEventManager manager = new OverdueBorrowEventManager("overdue");
        OverdueBarrowEventListener listener = mock(OverdueBarrowEventListener.class);
        manager.subscribe("overdue",listener);
        User user = mock(User.class);
        Item item = mock(Item.class);
        OverdueBorrowData event = new OverdueBorrowData(user, item, 5, LocalDate.now());
        manager.notify("overdue",event);
        verify(listener,times(1)).update("overdue",event);
    }

    @Test
    void notify_AfterUnsubscribe_DoesNotCallListener()
    {
        OverdueBorrowEventManager manager = new OverdueBorrowEventManager("overdue");
        OverdueBarrowEventListener listener1 = mock(OverdueBarrowEventListener.class);
        OverdueBarrowEventListener listener2 = mock(OverdueBarrowEventListener.class);
        manager.subscribe("overdue",listener1);
        manager.subscribe("overdue",listener2);
        manager.unsubscribe("overdue",listener1);
        User user = mock(User.class);
        Item item = mock(Item.class);
        OverdueBorrowData event = new OverdueBorrowData(user, item, 3, LocalDate.now());
        manager.notify("overdue",event);
        verify(listener1,never()).update(anyString(),any());
        verify(listener2,times(1)).update("overdue",event);
    }

    @Test
    void subscribeAndNotify_WithUnknownEventType_DoesNothingAndDoesNotThrow()
    {
        OverdueBorrowEventManager manager = new OverdueBorrowEventManager("overdue");
        OverdueBarrowEventListener listener = mock(OverdueBarrowEventListener.class);
        assertDoesNotThrow(() -> manager.subscribe("unknown",listener));
        User user = mock(User.class);
        Item item = mock(Item.class);
        OverdueBorrowData event = new OverdueBorrowData(user, item, 2, LocalDate.now());
        assertDoesNotThrow(() -> manager.notify("unknown",event));
        verify(listener,never()).update(anyString(),any());
    }
}
