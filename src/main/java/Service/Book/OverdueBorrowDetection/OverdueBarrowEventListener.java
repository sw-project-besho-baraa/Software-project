package Service.Book.OverdueBorrowDetection;

public interface OverdueBarrowEventListener {
    void update(String type, OverdueBorrowEvent event);
}
