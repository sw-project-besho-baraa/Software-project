package Service.UserService;

import Entity.User;
import Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service for retrieving a user's fine balance.
 */
@Service
public class GetUserBalanceService {

    private final UserRepository userRepository;

    /**
     * Creates a new service for fetching user balances.
     *
     * @param userRepository repository for accessing user data
     */
    @Autowired
    public GetUserBalanceService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Gets the fine balance of a user by their ID.
     *
     * @param userId ID of the user
     * @return user's fine balance, or {@code BigDecimal.ZERO} if not found
     */
    public BigDecimal getUserBalance(int userId) {
        return userRepository.findById(userId)
                .map(u -> u.getFineBalance() == null ? BigDecimal.ZERO : u.getFineBalance())
                .orElse(BigDecimal.ZERO);
    }
}
