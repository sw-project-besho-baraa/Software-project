package Service.LoginService_Test;

import DTO.UserDTO.UserCredentialsDTO;
import Entity.User;
import Repository.UserRepository;
import Service.LoginService;
import Session.ISessionManager;
import Validation.LoginCredentialsValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginService_Test
{
    private LoginCredentialsValidator loginCredentialsValidator;
    private ISessionManager sessionManager;
    private UserRepository userRepository;
    private LoginService loginService;

    @BeforeEach
    void setUp()
    {
        loginCredentialsValidator = mock(LoginCredentialsValidator.class);
        sessionManager = mock(ISessionManager.class);
        userRepository = mock(UserRepository.class);
        loginService = new LoginService(loginCredentialsValidator, sessionManager, userRepository);
    }

    @Test
    void login_UserNotFound_ReturnsFalse()
    {
        UserCredentialsDTO dto = new UserCredentialsDTO("besho@gmail.com", "123456");
        boolean result = loginService.login(dto);
        when(userRepository.findByEmail("besho@gmail.com")).thenReturn(Optional.empty());
        assertFalse(result);
    }

    @Test
    void login_InvalidCredentials_ReturnsFalse()
    {
        UserCredentialsDTO dto = new UserCredentialsDTO("besho@gmail.com", "123456");
        User user = mock(User.class);
        when(userRepository.findByEmail("besho@gmail.com")).thenReturn(Optional.of(user));
        when(loginCredentialsValidator.validateLogin(dto,user)).thenReturn(false);
        boolean result = loginService.login(dto);
        assertFalse(result);
    }

    @Test
    void login_ValidCredentials_ReturnsTrue()
    {
        UserCredentialsDTO dto = new UserCredentialsDTO("besho@gmail.com", "123456");
        User user = mock(User.class);
        when(userRepository.findByEmail("besho@gmail.com")).thenReturn(Optional.of(user));
        when(loginCredentialsValidator.validateLogin(dto,user)).thenReturn(true);
        boolean result = loginService.login(dto);
        assertTrue(result);
    }
}
