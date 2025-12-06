package Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import Enum.MediaItemType;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MediaItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(name = "is_borrowed")
    private boolean borrowed ;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User borrower;

    private Date borrowedDate;

    private Date dueDate;

    public MediaItem() {}

    public MediaItem(String title)
    {
        this.title = title;
    }

    public abstract MediaItemType getMediaType();
    @PrePersist
    protected void onCreate() {
        borrowedDate = new Date();
    }
}
