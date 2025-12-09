package Repository;

import Entity.MediaItem;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MediaItemRepository extends JpaRepository<@NonNull MediaItem, @NonNull Integer>
{
    List<MediaItem> findAllByBorrowedTrueAndDueDateBefore(@NonNull LocalDateTime date);

    long countByBorrowedTrueAndDueDateBefore(@NonNull LocalDateTime date);
}
