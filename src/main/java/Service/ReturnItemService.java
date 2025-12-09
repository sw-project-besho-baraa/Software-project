package Service;

import Entity.MediaItem;
import Entity.User;
import Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import Exception.ItemNotBorrowedByUserException;
import org.springframework.stereotype.Service;

@Service
public class ReturnItemService
{

    private final UserRepository userRepository;

    public ReturnItemService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Transactional
    public void returnItem(@NonNull User user,@NonNull MediaItem item)
    {
        if (!user.getBorrowedItems().contains(item))
        {
            throw new ItemNotBorrowedByUserException("User has not borrowed the provided item.");
        }
        item.setBorrowed(false);
        item.setBorrowedDate(null);
        item.setDueDate(null);
        item.setBorrower(null);

        user.getBorrowedItems().remove(item);

        userRepository.save(user);
    }
}
