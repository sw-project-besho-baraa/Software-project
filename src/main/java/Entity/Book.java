package Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
public class Book extends Item
{

    @Column(unique = true, nullable = false)
    private String isbn;
    @Column(nullable = false)
    private String author;
    @JoinColumn(name = "user_id")
    private User borrower;
    private Date borrowedDate;
    public Book()
    {
    }

    public Book(String title, String isbn, String author)
    {
        super(title);
        this.author = author;
        this.isbn = isbn;
    }

}
