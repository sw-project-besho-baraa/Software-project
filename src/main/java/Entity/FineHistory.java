package Entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "fine_history")
@NoArgsConstructor
public class FineHistory
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal fineAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "applied_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date appliedDate;

    public FineHistory(User user, BigDecimal fineAmount)
    {
        this.user = user;
        this.fineAmount = fineAmount;
        this.appliedDate = new Date();
    }
}