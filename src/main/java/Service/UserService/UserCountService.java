package Service.UserService;


import Repository.UserRepository;
import org.springframework.stereotype.Service;
import Enum.UserRole;

@Service
public class UserCountService {
    private final UserRepository userRepository;

    public UserCountService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long countUsersByRole(UserRole userRole) {
        return userRepository.countByUserRole(userRole);
    }

}
