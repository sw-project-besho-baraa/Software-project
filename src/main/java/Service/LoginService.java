package Service;

import DTO.UserDTO.UserCredentialsDTO;
import Repository.UserRepository;
import Session.ISessionManager;
import Validation.LoginCredentialsValidator;
import org.springframework.stereotype.Service;

/**
 * Handles user authentication and session initialization.
 * <p>
 * Validates user credentials, checks the user repository, and starts a new
 * session using an {@link ISessionManager} if authentication succeeds.
 */
@Service
public class LoginService {

    private final LoginCredentialsValidator loginCredentialsValidator;
    private final ISessionManager sessionManager;
    private final UserRepository userRepository;

    /**
     * Creates a new login service with its required dependencies.
     *
     * @param loginCredentialsValidator validator for verifying provided credentials
     * @param sessionManager            manages the authenticated user session
     * @param userRepository            repository for accessing stored user data
     */
    public LoginService(LoginCredentialsValidator loginCredentialsValidator,
                        ISessionManager sessionManager,
                        UserRepository userRepository) {
        this.loginCredentialsValidator = loginCredentialsValidator;
        this.sessionManager = sessionManager;
        this.userRepository = userRepository;
    }

    /**
     * Attempts to log in a user using the provided credentials.
     * <p>
     * The service validates the credentials and, on success, stores
     * the authenticated user in the active session.
     *
     * @param userDTO the DTO containing the user's email and password
     * @return {@code true} if authentication succeeds; {@code false} otherwise
     */
    public boolean login(UserCredentialsDTO userDTO) {
        var userOpt = userRepository.findByEmail(userDTO.getEmail());
        if (userOpt.isEmpty()) {
            return false;
        }

        var user = userOpt.get();
        if (!loginCredentialsValidator.validateLogin(userDTO, user)) {
            return false;
        }

        sessionManager.login(user);
        return true;
    }
}
