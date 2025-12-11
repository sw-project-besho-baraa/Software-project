package Service.UserService;

import Entity.MediaItem;
import Entity.User;
import Util.CurrentLocalTimeDateResolver.ICurrentLocalTimeDateResolver;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

/**
 * Service for managing and analyzing a user's borrowed media items.
 */
@Service
public class UserBorrowedItemsService
{

    /**
     * Counts the total number of borrowed items by a user.
     *
     * @param user
     *            the user to check
     * @return number of borrowed items
     */
    public int countBorrowedItems(@NonNull User user)
    {
        if (user.getBorrowedItems() == null)
        {
            return 0;
        }
        return user.getBorrowedItems().size();
    }

    /**
     * Counts the number of overdue borrowed items for a user.
     *
     * @param user
     *            the user to check
     * @param timeDateResolver
     *            provides the current local date and time
     * @return number of overdue items
     */
    public long countOverdueItems(@NonNull User user,ICurrentLocalTimeDateResolver timeDateResolver)
    {
        if (user.getBorrowedItems() == null)
        {
            return 0;
        }
        var currentLocalDateTime = timeDateResolver.getCurrentLocalDateTime();
        return user.getBorrowedItems().stream()
                .filter(mi -> mi.isBorrowed() && mi.getDueDate().isBefore(currentLocalDateTime)).count();
    }

    /**
     * Gets a stream of the user's overdue borrowed items.
     *
     * @param user
     *            the user to check
     * @param timeDateResolver
     *            provides the current local date and time
     * @return stream of overdue media items, or {@code null} if none
     */
    public Stream<MediaItem> getOverdueItems(@NonNull User user,ICurrentLocalTimeDateResolver timeDateResolver)
    {
        if (user.getBorrowedItems() == null)
        {
            return null;
        }
        var currentLocalDateTime = timeDateResolver.getCurrentLocalDateTime();
        return user.getBorrowedItems().stream()
                .filter(mi -> mi.isBorrowed() && mi.getDueDate().isBefore(currentLocalDateTime));
    }

    /**
     * Gets all borrowed items of a user.
     *
     * @param user
     *            the user whose items are retrieved
     * @return list of borrowed media items
     */
    public List<MediaItem> getBorrowedItems(@NonNull User user)
    {
        return user.getBorrowedItems();
    }
}
