package Entity;
import lombok.*;
import Enum.MediaItemType;
import jakarta.persistence.*;

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

    @Override
    public MediaItemType getMediaType() {
        return MediaItemType.Book;
    }

    public Book(String title, String isbn, String author)
    {
        super(title);
        this.author = author;
        this.isbn = isbn;
    }

}
