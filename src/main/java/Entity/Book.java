package Entity;

import jakarta.persistence.*;

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

    public String getIsbn()
    {
        return isbn;
    }

    public void setIsbn(String isbn)
    {
        this.isbn = isbn;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }
}
