package Service.Book.OverdueBorrowDetection;
import java.util.*;
public class OverdueBorrowEventManager {
    private final Map<String, List<OverdueBarrowEventListener>> listeners = new HashMap<>();

    public OverdueBorrowEventManager(String... operations) {
        for (String operation : operations) {
            listeners.put(operation, new ArrayList<>());
        }
    }

    public void subscribe(String eventType, OverdueBarrowEventListener listener) {
        List<OverdueBarrowEventListener> users = listeners.get(eventType);
        if (users != null) {
            users.add(listener);
        }
    }

    public void unsubscribe(String eventType, OverdueBarrowEventListener listener) {
        List<OverdueBarrowEventListener> users = listeners.get(eventType);
        if (users != null) {
            users.remove(listener);
        }
    }

    public void notify(String eventType, OverdueBorrowEvent event) {
        List<OverdueBarrowEventListener> users = listeners.get(eventType);
        if (users != null) {
            for (OverdueBarrowEventListener listener : users) {
                listener.update(eventType, event);
            }
        }
    }
}
