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

/**
 * Provides unified search functionality across all media item types
 * (books and CDs) using different search strategies.
 */
@Service
public class MediaItemSearchService {

    private final BookSearchService bookSearchService;
    private final CdSearchService cdSearchService;

    /**
     * Creates a new instance of the media item search service.
     *
     * @param bookSearchService service responsible for book searches
     * @param cdSearchService service responsible for CD searches
     */
    @Autowired
    public MediaItemSearchService(BookSearchService bookSearchService, CdSearchService cdSearchService) {
        this.bookSearchService = bookSearchService;
        this.cdSearchService = cdSearchService;
    }

    /**
     * Searches for media items based on the selected search mode and keyword.
     *
     * @param mode the selected search mode
     *             (e.g. "Title (Books & CDs)", "Book Author", "Book ISBN")
     * @param keyword the search keyword
     * @return a list of matching media items
     */
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
