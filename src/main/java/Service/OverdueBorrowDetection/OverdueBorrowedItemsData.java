package Service.OverdueBorrowDetection;

import Entity.User;
import java.util.List;

/**
 * Holds information about a user and their list of overdue borrowed items.
 *
 * @param user  the user who has overdue items
 * @param items list of the user's overdue borrowed items
 */
public record OverdueBorrowedItemsData(User user, List<OverdueBorrowedItem> items) { }
