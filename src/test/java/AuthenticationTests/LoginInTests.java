package AuthenticationTests;

import DTO.UserDTO;
import Entity.User;
import FakeHashing.FakeHashingPassword;
import FakeRepository.FakeUserRepository;
import FakeSession.FakeSessionManager;
import FakeValidation.FakeLoginCredentialsValidator;
import Service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginInTests {

    private LoginService loginService;
    private FakeUserRepository userRepository;
    private FakeLoginCredentialsValidator loginCredentialsValidator;
    private FakeSessionManager sessionManager;
    private FakeHashingPassword hashingPassword;

    @BeforeEach
    void setUp() {
        userRepository = new FakeUserRepository();
        hashingPassword = new FakeHashingPassword();
        loginCredentialsValidator = new FakeLoginCredentialsValidator(hashingPassword);
        sessionManager = new FakeSessionManager();

        loginService = new LoginService(loginCredentialsValidator, sessionManager, userRepository);
    }

    /**
     * Scenario 1:
     * User not found login() should return false,
     * validator must NOT be called,
     * and no session should be started.
     */
    @Test
    void loginReturnsFalseWhenUserNotFound() {
        UserDTO dto = new UserDTO("Bishawi@gmail.com", "letsholetsho");
        boolean result = loginService.login(dto);
        assertFalse(result, "Expected login to fail when user is not found");
        assertFalse(loginCredentialsValidator.wasCalled(), "Validator should NOT be called when user is missing");
        assertFalse(sessionManager.isLoggedIn(), "No user should be logged in");
    }

    /**
     * Scenario 2:
     * User exists in repository but credentials are invalid:
     *  login() should return false
     *  validator must be called with the right user & DTO
     *  no session should be started
     */
    @Test
    void loginReturnsFalseWhenCredentialsInvalid() {

        User existingUser = new User();
        existingUser.setEmail("Bishawi@gmail.com");
        userRepository.setStoredUser(existingUser);
        UserDTO dto = new UserDTO("Bishawi@gmail.com", "11112222");
        loginCredentialsValidator.setResultToReturn(false);
        boolean result = loginService.login(dto);
        assertFalse(result, "Expected login to fail when credentials are invalid");
        assertTrue(loginCredentialsValidator.wasCalled(), "Validator should be called when user exists");
        assertEquals(dto, loginCredentialsValidator.getLastDto(), "Validator should receive the same DTO");
        assertEquals(existingUser, loginCredentialsValidator.getLastUser(), "Validator should receive the same User");
        assertFalse(sessionManager.isLoggedIn(), "No user should be logged in when credentials are invalid");
        assertNull(sessionManager.getUser(), "Session should not hold any user");
    }

    /**
     * Scenario 3:
     * User exists and credentials are valid:
     *  login() should return true
     *  validator must be called
     *  session should store the logged-in user
     */
    @Test
    void loginReturnsTrueWhenCredentialsValidAndStartsSession() {
        User existingUser = new User();
        existingUser.setEmail("Bishawi@gmail.com");
        userRepository.setStoredUser(existingUser);
        UserDTO dto = new UserDTO("Bishawi@gmail.com", "letsholetsho");
        loginCredentialsValidator.setResultToReturn(true);
        boolean result = loginService.login(dto);
        assertTrue(result, "Expected login to succeed when credentials are valid");
        assertTrue(loginCredentialsValidator.wasCalled(), "Validator should be called");
        assertEquals(dto, loginCredentialsValidator.getLastDto(), "Validator should receive the same DTO");
        assertEquals(existingUser, loginCredentialsValidator.getLastUser(), "Validator should receive the same User");
        assertTrue(sessionManager.isLoggedIn(), "Session should be marked as logged in");
        assertEquals(existingUser, sessionManager.getUser(), "Session should store the logged-in user");
    }
}
