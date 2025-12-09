package Service_Test.UserService_Test;

import Entity.User;
import Repository.UserRepository;
import Service.UserService.GetUserBalanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class GetUserBalanceService_Test
{

    private UserRepository userRepository;
    private GetUserBalanceService getUserBalanceService;

    @BeforeEach
    void setUp()
    {
        userRepository = mock(UserRepository.class);
        getUserBalanceService = new GetUserBalanceService(userRepository);
    }

    @Test
    void getUserBalance_returnsCorrectBalance_whenUserExistsWithFine()
    {
        User user = new User();
        user.setFineBalance(BigDecimal.valueOf(25.5));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        BigDecimal result = getUserBalanceService.getUserBalance(1);
        verify(userRepository,times(1)).findById(1);
        assertEquals(BigDecimal.valueOf(25.5),result);
    }

    @Test
    void getUserBalance_returnsZero_whenUserExistsButFineBalanceIsNull()
    {
        User user = new User();
        user.setFineBalance(null);
        when(userRepository.findById(2)).thenReturn(Optional.of(user));
        BigDecimal result = getUserBalanceService.getUserBalance(2);
        verify(userRepository,times(1)).findById(2);
        assertEquals(BigDecimal.ZERO,result);
    }

    @Test
    void getUserBalance_returnsZero_whenUserNotFound()
    {
        when(userRepository.findById(3)).thenReturn(Optional.empty());
        BigDecimal result = getUserBalanceService.getUserBalance(3);
        verify(userRepository,times(1)).findById(3);
        assertEquals(BigDecimal.ZERO,result);
    }
}
