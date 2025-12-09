package Service.UserService;

import DTO.UserDTO.UserContactDTO;
import Entity.User;
import Repository.UserRepository;
import Util.HashingPassword.BCryptHashingPassword;
import Util.HashingPassword.IHashingPassword;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import Enum.UserRole;

@Service
public class AddUserService
{
   private final UserRepository userRepository;
   private final IHashingPassword hashingPassword;
    public AddUserService(UserRepository userRepository, BCryptHashingPassword hashingPassword)
    {
        this.userRepository = userRepository;
        this.hashingPassword = hashingPassword;
    }

    public void addUser(String name,String email,String password,UserRole role)
    {
        password = hashingPassword.hashPassword(password);
        User user = new User(name, email, password);
        user.setUserRole(role);
        userRepository.save(user);
    }

}
