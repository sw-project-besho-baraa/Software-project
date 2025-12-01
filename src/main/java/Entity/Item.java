package Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public abstract class Item
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    @Column(nullable = false)
    protected String title;
    protected boolean isBorrowed;
    protected Date borrowDate;
    public Item()
    {
    }

    public Item(String title)
    {
        this.title = title;
    }

}
