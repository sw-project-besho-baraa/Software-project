package Validation;

import DTO.UserDTO;
import Entity.User;
import Util.HashingPassword.IHashingPassword;

public class LoginCredentialsValidator
{
    private final IHashingPassword hashingPassword;

    public LoginCredentialsValidator(IHashingPassword hashingPassword)
    {
        this.hashingPassword = hashingPassword;
    }

    public boolean validateLogin(UserDTO userDTO,User user)
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
