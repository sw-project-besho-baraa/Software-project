package Service;

import Entity.MediaItem;
import Entity.User;
import Util.CurrentLocalTimeDateResolver.CurrentLocalDateTimeResolver;
import Util.DateCalculator.DateCalculator;
import lombok.Setter;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Date;
import Repository.UserRepository;
import Validation.BorrowValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Setter
@Getter
@Service
public class BorrowService
{

    private final UserRepository userRepository;
    private final BorrowValidator borrowValidator;

    public BorrowService(UserRepository userRepository, BorrowValidator borrowValidator)
    {
        this.userRepository = userRepository;
        this.borrowValidator = borrowValidator;
    }

    @Transactional
    public void borrow(@NonNull User user,@NonNull MediaItem item) throws Exception
    {
        borrowValidator.validate(user,item);
        var currentDate = new CurrentLocalDateTimeResolver().getCurrentLocalDateTime();
        int allowedOverdueDays = BorrowDueDurationCalculator.getDuration(item);
        LocalDateTime dueDate = DateCalculator.add(currentDate,allowedOverdueDays);
        item.setBorrowed(true);
        item.setBorrowedDate(currentDate);
        item.setDueDate(dueDate);
        item.setBorrower(user);
        user.getBorrowedItems().add(item);

        userRepository.save(user);

    }
}
