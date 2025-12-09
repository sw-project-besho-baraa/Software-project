package Service_Test.MediaItem_Test;

import Entity.MediaItem;
import Repository.MediaItemRepository;
import Service.MediaItem.AddMediaItemService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddMediaItemService_Test {

    @Test
    void constructor_throwsNullPointer_whenRepositoryIsNull() {
        assertThrows(NullPointerException.class, () -> new AddMediaItemService<MediaItem>(null));
    }

    @Test
    void constructor_doesNotUseRepository_whenValidRepositoryProvided() {
        MediaItemRepository<MediaItem> repo = mock(MediaItemRepository.class);
        assertDoesNotThrow(() -> new AddMediaItemService<>(repo));
        verifyNoInteractions(repo);
    }

    @Test
    void addItem_throwsNullPointer_whenItemIsNull() {
        MediaItemRepository<MediaItem> repo = mock(MediaItemRepository.class);
        AddMediaItemService<MediaItem> service = new AddMediaItemService<>(repo);
        assertThrows(NullPointerException.class, () -> service.addItem(null));
        verifyNoInteractions(repo);
    }

    @Test
    void addItem_doesNothingWithRepository_whenItemIsNotNull() {
        MediaItemRepository<MediaItem> repo = mock(MediaItemRepository.class);
        AddMediaItemService<MediaItem> service = new AddMediaItemService<>(repo);
        MediaItem item = mock(MediaItem.class);
        assertDoesNotThrow(() -> service.addItem(item));
        verifyNoInteractions(repo);
    }
}
