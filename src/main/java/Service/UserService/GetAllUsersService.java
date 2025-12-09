package Service.UserService;

import Entity.User;
import Repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GetAllUsersService
{

    private final UserRepository userRepository;

    public GetAllUsersService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }
}