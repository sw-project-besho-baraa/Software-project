package Service.MediaItem;

import Entity.MediaItem;
import Service.BookService.AllBookService;
import Service.CDService.AllCdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OverDueCountService {

    private final AllBookService allBookService;
    private final AllCdService allCdService;

    @Autowired
    public OverDueCountService(AllBookService allBookService, AllCdService allCdService) {
        this.allBookService = allBookService;
        this.allCdService = allCdService;
    }

    public long countOverdueItems() {
        List<MediaItem> allItems = new ArrayList<>();
        allItems.addAll(allBookService.getAllBooks());
        allItems.addAll(allCdService.getAllCds());

        return allItems.stream()
                .filter(MediaItem::isBorrowed)
                .filter(item -> item.getDueDate() != null && item.getDueDate().before(new Date()))
                .count();
    }
}
