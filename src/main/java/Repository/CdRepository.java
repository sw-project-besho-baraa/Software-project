package Repository;

import Entity.Cd;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CdRepository extends  JpaRepository<@NonNull Cd, @NonNull String>
{
    long count();

    List<Cd> findByTitleContainingIgnoreCase(@NonNull String title);
}
