package Validation.OverdueBorrowValidator;

import Entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import Exception.OverdueItemsException;
import Service.UserService.UserBorrowedItemsService;
import Util.CurrentLocalTimeDateResolver.CurrentLocalDateTimeResolver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Default validator that checks if a user has overdue borrowed items.
 */
@Component
public class StandardOverdueBorrowValidator implements IOverdueBorrowValidation
{

    /**
     * Validates if the given user has overdue borrowed items.
     * If overdue items exist, an {@link OverdueItemsException} is thrown.
     *
     * @param user the user to validate
     * @throws OverdueItemsException if user has overdue items
     * @see UserBorrowedItemsService
     * @see CurrentLocalDateTimeResolver
     */
    @Override
    public void validate(User user)
    {
        var userBorrowedItemsService = new UserBorrowedItemsService();
        var currentTimeDateResolver = new CurrentLocalDateTimeResolver();
        var overdueItems = userBorrowedItemsService.getOverdueItems(user,currentTimeDateResolver);
        if (overdueItems.findAny().isPresent())
        {
            throw new OverdueItemsException();
        }
    }
}
