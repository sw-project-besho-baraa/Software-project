package Service_Test.UserService_Test;


import Entity.User;
import Repository.UserRepository;
import Service.UserService.GetAllUsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class GetAllUsersService_Test {

    private UserRepository userRepository;
    private GetAllUsersService getAllUsersService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        getAllUsersService = new GetAllUsersService(userRepository);
    }

    @Test
    void getAllUsers_returnsListFromRepository() {
        List<User> expected = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(expected);
        List<User> result = getAllUsersService.getAllUsers();
        verify(userRepository, times(1)).findAll();
        assertEquals(expected, result);
    }

    @Test
    void getAllUsers_returnsEmptyList_whenRepositoryReturnsEmpty() {

        when(userRepository.findAll()).thenReturn(List.of());
        List<User> result = getAllUsersService.getAllUsers();
        verify(userRepository, times(1)).findAll();
        assertTrue(result.isEmpty());
    }
}