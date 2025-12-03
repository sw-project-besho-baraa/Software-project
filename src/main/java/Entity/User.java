package Entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.*;
import Enum.UserRole;
import java.util.*;

@Getter
@Setter
public class User
{
    private int id;
    private String name;
    private String email;
    private String hashedPassword;
    private Date creationDate;
    private UserRole userRole;
    @OneToMany(mappedBy = "borrower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> borrowedItems;

    public User(int id, String name, String email, String hashedPassword)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
    }
}
