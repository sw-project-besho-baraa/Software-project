package Validation.OverdueBorrowValidator;

import Entity.User;

/**
 * Used to check if a user has overdue borrowed items.
 */
public interface IOverdueBorrowValidation
{

    /**
     * Validates if the given user has overdue items or not.
     *
     * @param user
     *            user to validate
     */
    void validate(User user);
}
