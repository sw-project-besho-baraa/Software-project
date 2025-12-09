package Repository;

import Entity.Book;
import Entity.MediaItem;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<@NonNull Book, @NonNull String>
{
    long count();

    List<Book> findByTitleContainingIgnoreCase(@NonNull String title);

    List<Book> findByAuthorContainingIgnoreCase(@NonNull String author);

    List<Book> findByIsbnContainingIgnoreCase(@NonNull String isbn);

}