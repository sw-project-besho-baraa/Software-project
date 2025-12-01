package Service.Book;

import Entity.Book;
import Entity.User;
import Repository.UserRepository;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.transaction.Transactional;

@Setter
@Getter
public class BorrowBookService
{

    private UserRepository userRepository;
    public BorrowBookService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Transactional
    public void borrowBook(@NonNull User user,@NonNull Book book)
    {

        if (book.isBorrowed())
        {
            throw new IllegalArgumentException("Book is already borrowed");
        }

        book.setBorrowed(true);
        book.setBorrower(user);
        user.getBooks().add(book);
        userRepository.save(user);
    }
}
