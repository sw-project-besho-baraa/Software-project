package Repository;

import Entity.FineHistory;
import Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository for managing {@link FineHistory} records.
 * <p>
 * Provides access to users' fine transaction history.
 */
public interface FineHistoryRepository extends JpaRepository<FineHistory, Integer> {

    /**
     * Retrieves all fine history entries for the given user, ordered by date.
     *
     * @param user the user whose fine history is requested
     * @return list of fine history records in ascending date order
     */
    List<FineHistory> findByUserOrderByAppliedDateAsc(User user);
}
