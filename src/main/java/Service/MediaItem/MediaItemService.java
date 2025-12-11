package Service.MediaItem;

import Entity.MediaItem;
import Repository.MediaItemRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for managing media items in the system.
 */
@Service
public class MediaItemService {

    private final MediaItemRepository repository;

    /**
     * Creates a new MediaItemService with the given repository.
     *
     * @param repository repository used to access media items
     */
    public MediaItemService(@NonNull MediaItemRepository repository) {
        this.repository = repository;
    }

    /**
     * Adds a new media item to the repository.
     *
     * @param item the media item to add
     */
    public void addItem(@NonNull MediaItem item) {
        repository.save(item);
    }

    /**
     * Deletes a media item from the repository.
     *
     * @param item the media item to delete
     */
    public void deleteItem(@NonNull MediaItem item) {
        repository.delete(item);
    }

    /**
     * @return the total number of media items
     */
    public long getCountAllItems() {
        return repository.count();
    }

    /**
     * @return a list of all media items
     */
    public List<MediaItem> getAllItems() {
        return repository.findAll();
    }
}
