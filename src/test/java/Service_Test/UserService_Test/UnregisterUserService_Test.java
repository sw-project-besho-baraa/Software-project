package Service_Test.UserService_Test;

import Entity.User;
import Entity.MediaItem;
import Repository.UserRepository;
import Service.UserService.UnregisterUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UnregisterUserService_Test
{

    private UserRepository userRepository;
    private UnregisterUserService unregisterUserService;

    @BeforeEach
    void setUp()
    {
        userRepository = mock(UserRepository.class);
        unregisterUserService = new UnregisterUserService(userRepository);
    }

    @Test
    void unregisterUser_deletesUser_whenNoBorrowedItemsAndNoFines()
    {
        User user = new User();
        user.setId(1);
        user.setBorrowedItems(Collections.emptyList());
        user.setFineBalance(BigDecimal.ZERO);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        unregisterUserService.unregisterUser(user);
        verify(userRepository,times(1)).delete(user);
    }

    @Test
    void unregisterUser_throwsException_whenUserNotFound()
    {
        User user = new User();
        user.setId(2);
        when(userRepository.findById(2)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,() -> unregisterUserService.unregisterUser(user));
        verify(userRepository,never()).delete(any());
    }

    @Test
    void unregisterUser_throwsException_whenUserHasBorrowedItems()
    {
        User user = new User();
        user.setId(3);
        user.setBorrowedItems(Collections.singletonList(mock(MediaItem.class)));
        user.setFineBalance(BigDecimal.ZERO);
        when(userRepository.findById(3)).thenReturn(Optional.of(user));
        assertThrows(IllegalStateException.class,() -> unregisterUserService.unregisterUser(user));
        verify(userRepository,never()).delete(any());
    }

    @Test
    void unregisterUser_throwsException_whenUserHasUnpaidFines()
    {
        User user = new User();
        user.setId(4);
        user.setBorrowedItems(Collections.emptyList());
        user.setFineBalance(BigDecimal.valueOf(20));
        when(userRepository.findById(4)).thenReturn(Optional.of(user));
        assertThrows(IllegalStateException.class,() -> unregisterUserService.unregisterUser(user));
        verify(userRepository,never()).delete(any());
    }

    @Test
    void unregisterUser_deletesUser_whenBorrowedItemsAndFineBalanceAreNull()
    {
        User user = new User();
        user.setId(5);
        user.setBorrowedItems(null);
        user.setFineBalance(null);
        when(userRepository.findById(5)).thenReturn(Optional.of(user));
        unregisterUserService.unregisterUser(user);
        verify(userRepository,times(1)).delete(user);
    }
}
