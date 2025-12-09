package Service.UserService;

import Entity.MediaItem;
import Entity.User;
import Repository.MediaItemRepository;
import Repository.UserRepository;
import Util.CurrentLocalTimeDateResolver.ICurrentLocalTimeDateResolver;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
public class UserBorrowedItemsService
{
    public int countBorrowedItems(@NonNull User user)
    {

        if (user.getBorrowedItems() == null)
        {
            return 0;
        }
        return user.getBorrowedItems().size();
    }

    public long countOverdueItems(@NonNull User user, ICurrentLocalTimeDateResolver timeDateResolver)
    {
        if ( user.getBorrowedItems() == null)
        {
            return 0;
        }
        var currentLocalDateTime = timeDateResolver.getCurrentLocalDateTime();
        return user.getBorrowedItems().stream()
                .filter(mi -> mi.isBorrowed()  && mi.getDueDate().isBefore(currentLocalDateTime))
                .count();
    }
    public Stream<MediaItem> getOverdueItems(@NonNull User user, ICurrentLocalTimeDateResolver timeDateResolver)
    {
        if ( user.getBorrowedItems() == null)
        {
            return null;
        }
        var currentLocalDateTime = timeDateResolver.getCurrentLocalDateTime();
        return user.getBorrowedItems().stream()
                .filter(mi -> mi.isBorrowed()  && mi.getDueDate().isBefore(currentLocalDateTime));
    }

    public List<MediaItem> getBorrowedItems(@NonNull User user)
    {

        return  user.getBorrowedItems();
    }
}
