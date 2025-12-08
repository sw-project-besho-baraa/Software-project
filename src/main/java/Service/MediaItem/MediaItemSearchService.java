package Service.MediaItem;

import Entity.Book;
import Entity.Cd;
import Entity.MediaItem;
import Service.BookService.BookSearchService;
import Service.BookService.SearchStrategy.*;
import Service.CDService.CdSearchService;
import Service.CDService.SearchStrategy.CdSearchByTitleStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MediaItemSearchService {

    private final BookSearchService bookSearchService;
    private final CdSearchService cdSearchService;
    @Autowired
    public MediaItemSearchService(BookSearchService bookSearchService, CdSearchService cdSearchService) {
        this.bookSearchService = bookSearchService;
        this.cdSearchService = cdSearchService;
    }

    public List<MediaItem> searchByMode(String mode, String keyword) {
        List<MediaItem> result = new ArrayList<>();

        if (mode == null || keyword == null || keyword.isBlank())
            return result;

        switch (mode) {
            case "Title (Books & CDs)" -> {
                result.addAll(bookSearchService.search(new BookSearchByTitleStrategy(), keyword));
                result.addAll(cdSearchService.search(new CdSearchByTitleStrategy(), keyword));
            }
            case "Book Author" -> {
                result.addAll(bookSearchService.search(new BookSearchByAuthorStrategy(), keyword));
            }
            case "Book ISBN" -> {
                result.addAll(bookSearchService.search(new BookSearchByIsbnStrategy(), keyword));
            }
        }

        return result;
    }
}