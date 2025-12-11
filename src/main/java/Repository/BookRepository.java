package Repository;

import Entity.Book;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository for managing {@link Book} entities.
 * <p>
 * Provides basic CRUD operations and custom search queries.
 */
@Repository
public interface BookRepository extends JpaRepository<@NonNull Book, @NonNull String>
{

    /**
     * Counts total number of books.
     *
     * @return number of books
     */
    long count();

    /**
     * Finds books with titles containing the given value (case-insensitive).
     *
     * @param title
     *            part of the book title
     * @return list of matching books
     */
    List<Book> findByTitleContainingIgnoreCase(@NonNull String title);

    /**
     * Finds books with authors containing the given value (case-insensitive).
     *
     * @param author
     *            part of the author name
     * @return list of matching books
     */
    List<Book> findByAuthorContainingIgnoreCase(@NonNull String author);

    /**
     * Finds books with ISBNs containing the given value (case-insensitive).
     *
     * @param isbn
     *            part of the ISBN number
     * @return list of matching books
     */
    List<Book> findByIsbnContainingIgnoreCase(@NonNull String isbn);
}
