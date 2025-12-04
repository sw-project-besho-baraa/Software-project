package Service.Book.OverdueBorrowDetection;

import Repository.UserRepository;

public class RegularOverdueBookDetector extends OverdueBookDetector
{

    private static final int LIMIT = 28;
    public RegularOverdueBookDetector(UserRepository userRepository)
    {
        super(LIMIT, userRepository);
    }
}
