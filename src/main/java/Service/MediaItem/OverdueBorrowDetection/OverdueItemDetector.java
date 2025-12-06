package Service.MediaItem.OverdueBorrowDetection;

import Repository.UserRepository;
import lombok.NonNull;

import java.util.List;

public class OverdueItemDetector
{
    private final UserRepository userRepository;

    public OverdueItemDetector(@NonNull UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public List<OverdueBorrowedItemsData> detectUsersWithOverdueBooks()
    {
        return null;
    }
}
