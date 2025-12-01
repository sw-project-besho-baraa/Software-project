package Service.Book.OverdueBorrowDetection;
import Entity.Item;
import Entity.User;
import lombok.Getter;
import java.time.LocalDate;
public class OverdueBorrowEvent {
    private final User user;
    private final Item item;
    private final long overdueDays;
    private final LocalDate detectedAt;


    public OverdueBorrowEvent(User user, Item item, long overdueDays, LocalDate detectedAt) {
        this.user = user;
        this.item = item;
        this.overdueDays = overdueDays;
        this.detectedAt = detectedAt;
    }

    public String toMessage() {
        return "Item \"" + item.getTitle() + "\" is overdue by " + overdueDays + " days";
    }
}