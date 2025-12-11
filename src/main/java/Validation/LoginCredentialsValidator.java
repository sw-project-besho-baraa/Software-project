package Validation;

import DTO.UserDTO.UserCredentialsDTO;
import Entity.User;
import Util.HashingPassword.IHashingPassword;
import org.springframework.stereotype.Component;

/**
 * Validates login credentials for authentication.
 * <p>
 * This component checks whether the provided email and password match the
 * stored user credentials using a hashing utility.
 */
@Component
public class LoginCredentialsValidator
{
    private final IHashingPassword hashingPassword;

    /**
     * Constructs a new {@code LoginCredentialsValidator}.
     *
     * @param hashingPassword
     *            utility for verifying hashed passwords
     */
    public LoginCredentialsValidator(IHashingPassword hashingPassword)
    {
        this.hashingPassword = hashingPassword;
    }

    /**
     * Validates the provided user credentials against the stored user data.
     *
     * @param userDTO
     *            contains the email and raw password entered by the user
     * @param user
     *            the stored {@link User} entity retrieved from the database
     * @return true if the email matches and the password is valid; false otherwise
     */
    public boolean validateLogin(UserCredentialsDTO userDTO,User user)
    {

        if (user == null)
        {
            return false;
        }
        if (!userDTO.getEmail().equals(user.getEmail()))
        {
            return false;
        }
        return hashingPassword.verifyPassword(userDTO.getPassword(),user.getHashedPassword());
    }
}
