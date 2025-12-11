package Validation;

import Entity.MediaItem;
import Entity.User;
import Validation.OverdueBorrowValidator.IOverdueBorrowValidation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Validates whether a user is eligible to borrow an item.
 * <p>
 * Checks overdue items, unpaid fines, and item availability.
 */
@Service
public class BorrowValidator
{

    private final IOverdueBorrowValidation overdueBorrowValidation;

    /**
     * Creates a new {@code BorrowValidator}.
     *
     * @param overdueBorrowValidation
     *            component used to validate overdue borrows
     */
    public BorrowValidator(IOverdueBorrowValidation overdueBorrowValidation)
    {
        this.overdueBorrowValidation = overdueBorrowValidation;
    }

    /**
     * Validates if a user can borrow the given item.
     *
     * @param user
     *            the user attempting to borrow
     * @param item
     *            the media item to be borrowed
     * @throws Exception
     *             if item is already borrowed, user has fines, or overdue items
     *             exist
     * @see IOverdueBorrowValidation
     */
    public void validate(User user,MediaItem item) throws Exception
    {
        if (item.isBorrowed())
        {
            throw new IllegalArgumentException("Item is already borrowed");
        }
        if (user.getFineBalance() != null && user.getFineBalance().compareTo(BigDecimal.ZERO) > 0)
        {
            throw new IllegalStateException("fine: please pay full balance before borrowing.");
        }
        overdueBorrowValidation.validate(user);
        if (user.getFineBalance() != null && user.getFineBalance().compareTo(BigDecimal.ZERO) > 0)
        {
            throw new IllegalStateException("fine: please pay full balance before borrowing.");
        }
    }
}
