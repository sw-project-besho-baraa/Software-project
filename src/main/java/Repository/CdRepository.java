package Repository;

import Entity.Cd;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CdRepository extends MediaItemRepository<Cd>, JpaRepository<@NonNull Cd, @NonNull String>
{
    long count();

}
