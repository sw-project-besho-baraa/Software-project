package Validation;

import Entity.MediaItem;
import Entity.User;
import Validation.OverdueBorrowValidator.IOverdueBorrowValidation;

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

        overdueBorrowValidation.validate(user);
    }
}
