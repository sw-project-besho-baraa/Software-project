package Entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.*;
import Enum.UserRole;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Represents a library user with account, role, and fine details.
 */
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
    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Column(name = "fine_balance", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private BigDecimal fineBalance = BigDecimal.ZERO;

    @OneToMany(mappedBy = "borrower", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MediaItem> borrowedItems = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<FineHistory> fineHistoryList = new ArrayList<>();

    /**
     * Increases the user's fine balance.
     *
     * @param amount
     *            amount to add
     */
    public void increaseFine(@NonNull BigDecimal amount)
    {
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            return;
        if (fineBalance == null)
            fineBalance = BigDecimal.ZERO;
        fineBalance = fineBalance.add(amount);
    }

    /**
     * Decreases the user's fine balance.
     *
     * @param amount
     *            amount to subtract
     */
    public void decreaseFine(@NonNull BigDecimal amount)
    {
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            return;
        if (fineBalance == null)
            fineBalance = BigDecimal.ZERO;

        fineBalance = fineBalance.subtract(amount);
        if (fineBalance.compareTo(BigDecimal.ZERO) < 0)
            fineBalance = BigDecimal.ZERO;
    }

    /**
     * Creates a new user with basic info.
     *
     * @param name
     *            user's name
     * @param email
     *            user's email
     * @param hashedPassword
     *            hashed password
     */
    public User(String name, String email, String hashedPassword)
    {
        this.name = name;
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    /**
     * Returns a string representation of the user.
     *
     * @return user details as a string
     */
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * Sets the creation date when the user is first persisted.
     */
    @PrePersist
    protected void onCreate()
    {
        creationDate = LocalDateTime.now();
    }
}
