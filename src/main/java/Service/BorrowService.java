package Service;

import Entity.MediaItem;
import Entity.User;
import Util.CurrentLocalTimeDateResolver.CurrentLocalDateTimeResolver;
import Util.DateCalculator.DateCalculator;
import Repository.UserRepository;
import Validation.BorrowValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import lombok.NonNull;

/**
 * Handles the borrowing process for media items.
 */
@Service
public class BorrowService {

    private final UserRepository userRepository;
    private final BorrowValidator borrowValidator;

    /**
     * Creates a new borrow service with the given dependencies.
     *
     * @param userRepository repository for saving user data
     * @param borrowValidator validator for borrow rules
     */
    public BorrowService(UserRepository userRepository, BorrowValidator borrowValidator) {
        this.userRepository = userRepository;
        this.borrowValidator = borrowValidator;
    }

    /**
     * Allows a user to borrow a media item if validation passes.
     *
     * @param user the user borrowing the item
     * @param item the item being borrowed
     * @throws Exception if the borrow validation fails
     */
    @Transactional
    public void borrow(@NonNull User user, @NonNull MediaItem item) throws Exception {
        borrowValidator.validate(user, item);
        var currentDate = new CurrentLocalDateTimeResolver().getCurrentLocalDateTime();
        int allowedOverdueDays = BorrowDueDurationCalculator.getDuration(item);
        var dueDate = DateCalculator.add(currentDate, allowedOverdueDays);

        item.setBorrowed(true);
        item.setBorrowedDate(currentDate);
        item.setDueDate(dueDate);
        item.setBorrower(user);
        user.getBorrowedItems().add(item);

        userRepository.save(user);
    }
}
