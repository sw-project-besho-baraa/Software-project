package Repository;

import Entity.User;

import java.util.List;
import java.util.Optional;

import Service.MediaItem.OverdueBorrowDetection.OverdueBorrowedItemsData;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import Enum.UserRole;

@Repository
public interface UserRepository extends JpaRepository<@NonNull User, @NonNull Integer>
{
    Optional<User> findByEmail(@NonNull String email);

    long countByUserRole(@NonNull UserRole userRole);

    @Query("select u from User u left join fetch u.borrowedItems where u.id = :id")
    Optional<User> findByIdWithBorrowedItems(@Param("id") Integer id);
}