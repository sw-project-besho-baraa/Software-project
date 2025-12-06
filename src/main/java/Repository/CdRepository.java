package Repository;

import Entity.Cd;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CdRepository extends MediaItemRepository<Cd>, JpaRepository<@NonNull Cd, @NonNull String>
{

}
