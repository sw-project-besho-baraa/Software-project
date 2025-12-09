package Service.MediaItem;

import Entity.MediaItem;
import Repository.MediaItemRepository;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;

public class MediaItemService
{
    private final MediaItemRepository repository;

    public MediaItemService(@NonNull MediaItemRepository repository)
    {
        this.repository = repository;
    }

    public void addItem(@NonNull MediaItem item)
    {
         repository.save(item);
    }
    public void deleteItem(@NonNull MediaItem item)
    {
        repository.delete(item);
    }
    public long getCountAllItems()
    {
        return repository.count();
    }
    public List<MediaItem> getAllItems()
    {
        return repository.findAll();
    }
}
