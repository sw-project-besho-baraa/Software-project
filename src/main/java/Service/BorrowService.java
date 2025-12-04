package Service;

import Entity.Item;
import Entity.User;
import Repository.UserRepository;
import Validation.BorrowValidator;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import javax.transaction.Transactional;
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
    public void borrow(@NonNull User user,@NonNull Item item) throws Exception
    {
        borrowValidator.validate(user,item);
        item.setBorrowed(true);
        item.setBorrower(user);
        user.getBorrowedItems().add(item);
        userRepository.save(user);
    }

}
