package Service;

import DTO.UserDTO;
import Repository.UserRepository;
import Session.ISessionManager;
import Validation.LoginCredentialsValidator;

public class LoginService
{

    private final LoginCredentialsValidator loginCredentialsValidator;
    private final ISessionManager sessionManager;
    private final UserRepository userRepository;
    public LoginService(LoginCredentialsValidator loginCredentialsValidator, ISessionManager sessionManager,
            UserRepository userRepository)
    {
        this.loginCredentialsValidator = loginCredentialsValidator;
        this.sessionManager = sessionManager;
        this.userRepository = userRepository;
    }

    public boolean login(UserDTO userDTO)
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
