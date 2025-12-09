package Validation;

import Entity.MediaItem;
import Entity.User;
import Validation.OverdueBorrowValidator.IOverdueBorrowValidation;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BorrowValidator
{

    private final IOverdueBorrowValidation overdueBorrowValidation;
    public BorrowValidator(IOverdueBorrowValidation overdueBorrowValidation)
    {
        this.overdueBorrowValidation = overdueBorrowValidation;
    }

    public void validate(User user,MediaItem item) throws Exception
    {
        if (item.isBorrowed())
        {
            throw new IllegalArgumentException("Item is already borrowed");
        }
        if (user.getFineBalance() != null && user.getFineBalance().compareTo(BigDecimal.ZERO) > 0) {
            throw new IllegalStateException("fine: please pay full balance before borrowing.");
        }
        overdueBorrowValidation.validate(user);
        if (user.getFineBalance() != null && user.getFineBalance().compareTo(BigDecimal.ZERO) > 0) {
            throw new IllegalStateException("fine: please pay full balance before borrowing.");
        }
    }
}
