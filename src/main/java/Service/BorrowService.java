package Service;

import Entity.User;
import Util.DateCalculator.DateCalculator;
import lombok.Setter;
import lombok.Getter;
import lombok.NonNull;
import java.util.Date;
import Entity.MediaItem;
import Repository.UserRepository;
import Validation.BorrowValidator;
import jakarta.transaction.Transactional;

@Setter
@Getter
public class BorrowService
{
    private UserRepository userRepository;
    private BorrowValidator borrowValidator;
    public BorrowService(UserRepository userRepository, BorrowValidator borrowValidator)
    {
        this.userRepository = userRepository;
        this.borrowValidator = borrowValidator;
    }

    @Transactional
    public void borrow(@NonNull User user,@NonNull MediaItem item) throws Exception
    {
        borrowValidator.validate(user,item);
        Date currentDate = new Date();
        Date dueDate = DateCalculator.add(currentDate,28);// adjust it
        item.setBorrowed(true);
        item.setBorrowedDate(currentDate);
        item.setDueDate(dueDate);
        item.setBorrower(user);
        // user.getBorrowedItems().add(item);
        userRepository.save(user);
    }

}
