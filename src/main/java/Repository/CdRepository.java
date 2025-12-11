package Repository;

import Entity.Cd;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository for managing {@link Cd} entities.
 * <p>
 * Provides CRUD operations and CD-specific queries.
 */
@Repository
public interface CdRepository extends JpaRepository<@NonNull Cd, @NonNull String>
{

    /**
     * Counts total number of CDs.
     *
     * @return number of CDs
     */
    long count();

    /**
     * Finds CDs with titles containing the given value (case-insensitive).
     *
     * @param title
     *            part of the CD title
     * @return list of matching CDs
     */
    List<Cd> findByTitleContainingIgnoreCase(@NonNull String title);
}
