package Entity;

import lombok.*;
import Enum.MediaItemType;
import jakarta.persistence.*;

/**
 * Represents a book item in the library.
 */
@Setter
@Getter
@Entity
@NoArgsConstructor
public class Book extends MediaItem
{

    @Column(unique = true, nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String author;

    /**
     * Returns the media type for this item.
     *
     * @return Book type
     */
    @Override
    public MediaItemType getMediaType()
    {
        return MediaItemType.Book;
    }

    /**
     * Creates a new Book with title, ISBN and author.
     *
     * @param title
     *            book title
     * @param isbn
     *            book ISBN
     * @param author
     *            book author
     */
    public Book(String title, String isbn, String author)
    {
        super(title);
        this.author = author;
        this.isbn = isbn;
    }
}
