package ValidationTests;

import DTO.UserDTO;
import Entity.User;
import FakeHashing.FakeHashingPassword;
import Util.HashingPassword.IHashingPassword;
import Validation.LoginCredentialsValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class LoginCredentialsValidatorTests {
    private LoginCredentialsValidator validator;
    private FakeHashingPassword fakeHashing;
    @BeforeEach
    void setUp() {
        fakeHashing = new FakeHashingPassword();
        validator = new LoginCredentialsValidator(fakeHashing);
    }

    @Test
    void validateLoginReturnsFalseWhenUserIsNull() {
        UserDTO dto = new UserDTO("bishawi@example.com", "password123");
        boolean result = validator.validateLogin(dto, null);
        assertFalse(result, "Expected validation to fail when user is null");
        assertFalse(fakeHashing.wasCalled(), "hashing verifyPassword should not be called when user is null");
    }

    @Test
    void validateLoginReturnsFalseWhenEmailsDoNotMatch() {
        UserDTO dto = new UserDTO("bishawi@example.com", "password123");
        User user = new User();
        user.setEmail("other@example.com");
        user.setHashedPassword("someHash");
        boolean result = validator.validateLogin(dto, user);
        assertFalse(result, "Expected validation to fail when emails do not match");
        assertFalse(fakeHashing.wasCalled(), "Hashing verifyPassword should NOT be called when emails differ");
    }


    @Test
    void validateLoginReturnsFalseWhenPasswordDoesNotMatch() {
        UserDTO dto = new UserDTO("bishawi@example.com", "wrong-password");
        User user = new User();
        user.setEmail("bishawi@example.com");
        user.setHashedPassword("storedHash");
        fakeHashing.setResultToReturn(false);
        boolean result = validator.validateLogin(dto, user);
        assertFalse(result, "Expected validation to fail when password is invalid");
        assertTrue(fakeHashing.wasCalled(), "Hashing verifyPassword should be called when emails match");
        assertEquals(dto.getPassword(), fakeHashing.getLastPlainPassword(), "Plain password should be passed to hashing");
        assertEquals(user.getHashedPassword(), fakeHashing.getLastHashedPassword(), "Stored hash should be passed to hashing");
    }

    @Test
    void validateLoginReturnsTrueWhenCredentialsAreValid() {
        UserDTO dto = new UserDTO("bishawi@example.com", "correct-password");
        User user = new User();
        user.setEmail("bishawi@example.com");
        user.setHashedPassword("storedHash");
        fakeHashing.setResultToReturn(true);
        boolean result = validator.validateLogin(dto, user);
        assertTrue(result, "Expected validation to succeed when credentials are valid");
        assertTrue(fakeHashing.wasCalled(), "Hashing verifyPassword should be called");
        assertEquals(dto.getPassword(), fakeHashing.getLastPlainPassword(), "Plain password should be passed to hashing");
        assertEquals(user.getHashedPassword(), fakeHashing.getLastHashedPassword(), "Stored hash should be passed to hashing");
    }
}
