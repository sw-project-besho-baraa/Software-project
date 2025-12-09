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

@Component
public class StandardOverdueBorrowValidator implements IOverdueBorrowValidation
{

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
