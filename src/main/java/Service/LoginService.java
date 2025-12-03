package Service;

import DTO.UserDTO.UserCredentialsDTO;
import Repository.UserRepository;
import Session.ISessionManager;
import Validation.LoginCredentialsValidator;

/**
 * Service responsible for authenticating users and establishing a session.
 * <p>
 * It validates provided credentials, checks the user repository, and delegates
 * session establishment to an {@link ISessionManager}.
 */
public class LoginService
{

    private final LoginCredentialsValidator loginCredentialsValidator;
    private final ISessionManager sessionManager;
    private final UserRepository userRepository;

    /**
     * Constructs a new LoginService with its required collaborators.
     *
     * @param loginCredentialsValidator
     *            validator used to verify provided credentials against stored user
     *            data
     * @param sessionManager
     *            session manager responsible for maintaining the current user
     *            session
     * @param userRepository
     *            repository used to load users by email (or other identifiers)
     */
    public LoginService(LoginCredentialsValidator loginCredentialsValidator, ISessionManager sessionManager,
            UserRepository userRepository)
    {
        this.loginCredentialsValidator = loginCredentialsValidator;
        this.sessionManager = sessionManager;
        this.userRepository = userRepository;
    }

    /**
     * Attempts to authenticate a user with the provided credentials and start a
     * session on success.
     *
     * @param userDTO
     *            contains the email and password to authenticate
     * @return true if authentication succeeds and a session is started; false
     *         otherwise
     */
    public boolean login(UserCredentialsDTO userDTO)
    {
        var userOpt = userRepository.findByEmail(userDTO.getEmail());

        if (userOpt.isEmpty())
        {
            return false;
        }
        var user = userOpt.get();
        if (!loginCredentialsValidator.validateLogin(userDTO,user))
        {
            return false;
        }
        sessionManager.login(user);
        return true;
    }
}
