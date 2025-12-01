package Validation;

import Entity.Item;
import Entity.User;
import Validation.OverdueBorrowValidator.IOverdueBorrowValidation;

public class BorrowValidator
{

    private final IOverdueBorrowValidation overdueBorrowValidation;
    public BorrowValidator(IOverdueBorrowValidation overdueBorrowValidation)
    {
        this.overdueBorrowValidation = overdueBorrowValidation;
    }

    public void validate(User user,Item item) throws Exception
    {
        if (item.isBorrowed())
        {
            throw new IllegalArgumentException("Item is already borrowed");
        }
        overdueBorrowValidation.validate(user);
    }
}
