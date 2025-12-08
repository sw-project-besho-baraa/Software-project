package Service.UserService;

import Entity.MediaItem;
import Entity.User;
import Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserBorrowedItemsService {

    private final UserRepository userRepository;

    public UserBorrowedItemsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User loadUserWithBorrowedItems(User sessionUser) {
        if (sessionUser == null) {
            return null;
        }
        return userRepository.findByIdWithBorrowedItems(sessionUser.getId())
                .orElse(null);
    }

    public int countBorrowedItems(User sessionUser) {
        User user = loadUserWithBorrowedItems(sessionUser);
        if (user == null || user.getBorrowedItems() == null) {
            return 0;
        }
        return user.getBorrowedItems().size();
    }

    public long countOverdueItems(User sessionUser) {
        User user = loadUserWithBorrowedItems(sessionUser);
        if (user == null || user.getBorrowedItems() == null) {
            return 0;
        }
        Date now = new Date();
        return user.getBorrowedItems().stream()
                .filter(mi -> mi.isBorrowed()
                        && mi.getDueDate() != null
                        && mi.getDueDate().before(now))
                .count();
    }

    public List<MediaItem> getBorrowedItems(User sessionUser) {
        User user = loadUserWithBorrowedItems(sessionUser);
        if (user == null || user.getBorrowedItems() == null) {
            return List.of();
        }
        return new ArrayList<>(user.getBorrowedItems());
    }
}
