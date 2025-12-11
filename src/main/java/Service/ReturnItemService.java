package Service;

import Entity.MediaItem;
import Entity.User;
import Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import Exception.ItemNotBorrowedByUserException;
import org.springframework.stereotype.Service;

/**
 * Handles the process of returning borrowed media items.
 * <p>
 * Ensures the item belongs to the user's borrowed collection,
 * then marks it as returned and updates the repository.
 */
@Service
public class ReturnItemService {

    private final UserRepository userRepository;

    /**
     * Creates a new {@code ReturnItemService}.
     *
     * @param userRepository the repository used to persist user and item changes
     */
    public ReturnItemService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Returns a borrowed item for a given user.
     * <p>
     * If the user did not borrow the specified item,
     * an {@link ItemNotBorrowedByUserException} is thrown.
     * This operation is transactional to ensure data consistency.
     *
     * @param user the user returning the item
     * @param item the borrowed item to be returned
     * @throws ItemNotBorrowedByUserException if the user did not borrow the specified item
     */
    @Transactional
    public void returnItem(@NonNull User user, @NonNull MediaItem item) {
        if (!user.getBorrowedItems().contains(item)) {
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
