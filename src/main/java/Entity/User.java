package Entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.*;
import Enum.UserRole;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String hashedPassword;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date creationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @OneToMany(mappedBy = "borrower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MediaItem> borrowedItems = new ArrayList<>();


    public User( String name, String email, String hashedPassword)
    {
        this.name = name;
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
    }
    @PrePersist
    protected void onCreate() {
        creationDate = new Date();
    }
}
