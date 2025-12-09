package Service.UserService;

import Entity.User;
import Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class GetUserBalanceService
{

    private final UserRepository userRepository;
    @Autowired
    public GetUserBalanceService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public BigDecimal getUserBalance(int userId)
    {
        return userRepository.findById(userId)
                .map(u -> u.getFineBalance() == null ? BigDecimal.ZERO : u.getFineBalance()).orElse(BigDecimal.ZERO);
    }
}
