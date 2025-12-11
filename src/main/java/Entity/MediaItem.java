package Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import Enum.MediaItemType;

/**
 * Base class for all media items in the library (e.g., books, CDs).
 */
@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MediaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(name = "is_borrowed")
    private boolean borrowed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User borrower;

    private LocalDateTime borrowedDate;
    private LocalDateTime dueDate;
    private LocalDateTime lastTimeFineCalculated;

    /**
     * Default constructor.
     */
    public MediaItem() {
    }

    /**
     * Creates a new media item with the given title.
     *
     * @param title item title
     */
    public MediaItem(String title) {
        this.title = title;
    }

    /**
     * Returns the specific media type of this item.
     *
     * @return type of media item
     * @see MediaItemType
     */
    public abstract MediaItemType getMediaType();

    /**
     * Sets the borrowed date automatically when the item is first persisted.
     */
    @PrePersist
    public void onCreate() {
        borrowedDate = LocalDateTime.now();
    }
}
