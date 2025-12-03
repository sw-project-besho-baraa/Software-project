package Validation.OverdueBorrowValidator;

import Entity.User;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import Exception.OverdueItemsException;
import Service.Book.OverdueBorrowDetection.OverdueBorrowEvent;
import Service.Book.OverdueBorrowDetection.OverdueBorrowEventManager;

import java.util.List;
import java.util.Objects;

public class StandardOverdueBorrowValidator implements IOverdueBorrowValidation
{

    private final int limit = 28;
    public final OverdueBorrowEventManager events = new OverdueBorrowEventManager("overdue");
    @Override
    public void validate(User user)
    {

        LocalDate today = LocalDate.now();

        List<String> overdueMessages = user.getBorrowedItems().stream().map(item -> {
            LocalDate borrowedLocalDate = item.getBorrowedDate().toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDate();

            long daysBetween = ChronoUnit.DAYS.between(borrowedLocalDate,today);

            if (daysBetween > limit)
            {
                long overdueDays = daysBetween - limit;
                OverdueBorrowEvent event = new OverdueBorrowEvent(user, item, overdueDays, today);
                events.notify("overdue", event);
                return "item \"" + item.getTitle() + "\" is overdue by " + overdueDays + " days";
            }
            return null;
        }).filter(Objects::nonNull).toList();

        if (!overdueMessages.isEmpty())
        {
            throw new OverdueItemsException(overdueMessages);
        }
    }
}
