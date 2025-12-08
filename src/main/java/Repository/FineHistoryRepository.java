package Repository;

import Entity.FineHistory;
import Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FineHistoryRepository extends JpaRepository<FineHistory, Integer> {
    List<FineHistory> findByUserOrderByAppliedDateAsc(User user);
}