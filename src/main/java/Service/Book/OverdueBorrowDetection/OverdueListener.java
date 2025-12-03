package Service.Book.OverdueBorrowDetection;

public class OverdueListener implements OverdueBarrowEventListener{
    @Override
    public void update(String type, OverdueBorrowEvent event) {
        System.out.println(event.toMessage());
    }
}
