package Repository;

import Entity.User;

import java.util.List;
import java.util.Optional;

import Service.Book.OverdueBorrowDetection.OverdueBorrowedItemsData;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<@NonNull User, @NonNull Integer>
{
    Optional<User> findByEmail(@NonNull String email);

    List<OverdueBorrowedItemsData> findUsersWithBookingsExceedingDuration(int days);
}