package Entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.*;
import Enum.UserRole;

import java.math.BigDecimal;
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

    @Column(name = "fine_balance", precision = 10, scale = 2,
            columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private BigDecimal fineBalance = BigDecimal.ZERO;

    @OneToMany(mappedBy = "borrower", cascade = CascadeType.ALL)
    private List<MediaItem> borrowedItems = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FineHistory> fineHistoryList = new ArrayList<>();

    public void increaseFine(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) return;
        if (fineBalance == null) fineBalance = BigDecimal.ZERO;
        fineBalance = fineBalance.add(amount);
    }

    public void decreaseFine(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) return;
        if (fineBalance == null) fineBalance = BigDecimal.ZERO;

        fineBalance = fineBalance.subtract(amount);
        if (fineBalance.compareTo(BigDecimal.ZERO) < 0) {
            fineBalance = BigDecimal.ZERO;
        }
    }

    public User(String name, String email, String hashedPassword)
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
    protected void onCreate()
    {
        creationDate = new Date();
    }
}
