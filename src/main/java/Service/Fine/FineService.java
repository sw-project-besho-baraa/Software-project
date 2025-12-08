package Service.Fine;

import Entity.FineHistory;
import Entity.MediaItem;
import Entity.User;
import Enum.MediaItemType;
import Repository.FineHistoryRepository;
import Repository.UserRepository;
import Validation.AmountValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
@Service
public class FineService {

    private final UserRepository userRepository;
    private final FineHistoryRepository fineHistoryRepository;
    private final AmountValidator amountValidator;

    public FineService(UserRepository userRepository,
                       FineHistoryRepository fineHistoryRepository,
                       AmountValidator amountValidator) {
        this.userRepository = userRepository;
        this.fineHistoryRepository = fineHistoryRepository;
        this.amountValidator = amountValidator;
    }

    public BigDecimal calculateFine(MediaItem item, int overdueDays) {
        if (item == null || overdueDays <= 0) return BigDecimal.ZERO;
        FineStrategy strategy = strategyFor(item.getMediaType());
        return Objects.requireNonNullElse(strategy.calculateFine(overdueDays), BigDecimal.ZERO);
    }

    public void applyFine(User user, MediaItem item, int overdueDays) {
        BigDecimal fine = calculateFine(item, overdueDays);
        if (fine.compareTo(BigDecimal.ZERO) > 0) {
            user.increaseFine(fine);
            FineHistory fineHistory = new FineHistory(user, fine);
            fineHistoryRepository.save(fineHistory);
            userRepository.save(user);
        }
    }

    public void payFine(User user, String rawAmount) {
        if (user == null) {
            throw new IllegalArgumentException("User is required.");
        }

        BigDecimal parsed = amountValidator.validateAndParse(rawAmount);
        if (parsed == null || parsed.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        BigDecimal balance = user.getFineBalance() == null ? BigDecimal.ZERO : user.getFineBalance();
        if (balance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("User has no outstanding fines to pay.");
        }
        if (parsed.compareTo(balance) > 0) {
            throw new IllegalArgumentException("Paid amount exceeds the current fine balance.");
        }

        payFineInternal(user, parsed);
    }

    private void payFineInternal(User user, BigDecimal amount) {
        if (user == null || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) return;
        user.decreaseFine(amount);
        FineHistory fineHistory = new FineHistory(user, amount.negate());
        userRepository.save(user);
        fineHistoryRepository.save(fineHistory);
    }

    private FineStrategy strategyFor(MediaItemType type) {
        return switch (type) {
            case Book -> new BookFineStrategy();
            case Cd -> new CDFineStrategy();
            default -> new BookFineStrategy();
        };
    }
}
