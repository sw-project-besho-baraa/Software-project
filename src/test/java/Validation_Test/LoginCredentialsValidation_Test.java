package Validation_Test;

import DTO.UserDTO.UserCredentialsDTO;
import Entity.User;
import Util.HashingPassword.IHashingPassword;
import Validation.LoginCredentialsValidator;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginCredentialsValidation_Test
{
    private IHashingPassword hashingPassword;
    private LoginCredentialsValidator validator;

    @BeforeEach
    void setUp()
    {
        hashingPassword = mock(IHashingPassword.class);
        validator = new LoginCredentialsValidator(hashingPassword);
    }

    @Test
    void validateLogin_WithCorrectEmailAndValidPassword_ReturnsTrue()
    {
        UserCredentialsDTO dto = new UserCredentialsDTO("besho@gmail.com", "123456");
        User user = mock(User.class);
        when(user.getEmail()).thenReturn("besho@gmail.com");
        when(user.getHashedPassword()).thenReturn("$2a$12$f/7CRbI3/q2TS.EJMoUyHOT0CzN06iUUltla7Cidxqo.gO6DL5WGi");
        when(hashingPassword.verifyPassword("123456","$2a$12$f/7CRbI3/q2TS.EJMoUyHOT0CzN06iUUltla7Cidxqo.gO6DL5WGi"))
                .thenReturn(true);
        boolean result = validator.validateLogin(dto,user);
        assertTrue(result);
    }

    @Test
    void invalidLogin_WithCorrectEmailAndInvalidPassword_ReturnsFalse()
    {
        UserCredentialsDTO dto = new UserCredentialsDTO("besho@gmail.com", "123");
        User user = mock(User.class);
        when(user.getEmail()).thenReturn("besho@gmail.com");
        when(user.getHashedPassword()).thenReturn("$2a$12$f/7CRbI3/q2TS.EJMoUyHOT0CzN06iUUltla7Cidxqo.gO6DL5WGi");
        when(hashingPassword.verifyPassword("123","$2a$12$f/7CRbI3/q2TS.EJMoUyHOT0CzN06iUUltla7Cidxqo.gO6DL5WGi"))
                .thenReturn(false);
        boolean result = validator.validateLogin(dto,user);
        assertFalse(result);
    }

    @Test
    void invalidLogin_WithInvalidEmailAndCorrectPassword_ReturnsFalse()
    {
        UserCredentialsDTO dto = new UserCredentialsDTO("besho@gmail.com", "123456");
        User user = mock(User.class);
        when(user.getEmail()).thenReturn("beshoinvalid@gmail.com");
        when(user.getHashedPassword()).thenReturn("$2a$12$f/7CRbI3/q2TS.EJMoUyHOT0CzN06iUUltla7Cidxqo.gO6DL5WGi");
        boolean result = validator.validateLogin(dto,user);
        assertFalse(result);
    }

    @Test
    void invalidLogin_WithNullUser_ReturnsFalse()
    {
        UserCredentialsDTO dto = new UserCredentialsDTO("besho@gmail.com", "123456");
        User user = null;
        boolean result = validator.validateLogin(dto,user);
        assertFalse(result);
    }

}
