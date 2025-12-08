package Service;

import Entity.MediaItem;
import Entity.User;
import Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ReturnService {

    private final UserRepository userRepository;

    public ReturnService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void returnItem(@NonNull User sessionUser, @NonNull MediaItem item) {
        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new IllegalStateException("User not found."));

        MediaItem toReturn = user.getBorrowedItems().stream()
                .filter(mi -> mi.getId() == item.getId())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("This item is not in user's borrowed items."));

        if (!toReturn.isBorrowed() || toReturn.getBorrower() == null) {
            throw new IllegalStateException("Item is not currently borrowed.");
        }

        toReturn.setBorrowed(false);
        toReturn.setBorrowedDate(null);
        toReturn.setDueDate(null);
        toReturn.setBorrower(null);

        user.getBorrowedItems().remove(toReturn);

        userRepository.save(user);
    }
}
