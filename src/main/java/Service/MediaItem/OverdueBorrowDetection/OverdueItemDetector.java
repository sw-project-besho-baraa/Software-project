package Service.Book.OverdueBorrowDetection;

import Repository.UserRepository;

import java.util.List;

public abstract class OverdueItemDetector
{
    private final int limit;
    private final UserRepository userRepository;

    public OverdueItemDetector(int limit, UserRepository userRepository)
    {
        this.userRepository = userRepository;
        this.limit = limit;
    }

    public List<OverdueBorrowedItemsData> detectUsersWithOverdueBooks()
    {
        return userRepository.findUsersWithBookingsExceedingDuration(limit);
    }
}
