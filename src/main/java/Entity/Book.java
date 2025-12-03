package Entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
public class Book extends Item
{

    @Column(unique = true, nullable = false)
    private String isbn;
    @Column(nullable = false)
    private String author;
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
