package Service.Book;

import Entity.MediaItem;
import Repository.MediaItemRepository;
import lombok.NonNull;

public class AddMediaItemService<TMediaItem extends MediaItem>
{
    private final MediaItemRepository<TMediaItem> repository;
    public AddMediaItemService(@NonNull MediaItemRepository<TMediaItem>  repository)
    {
        this.repository = repository;
    }

    public void addItem(@NonNull TMediaItem item)
    {
        repository.save(item);
    }
}
