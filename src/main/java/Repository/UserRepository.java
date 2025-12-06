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

@Repository
public interface UserRepository extends JpaRepository<@NonNull User, @NonNull Integer>
{
    Optional<User> findByEmail(@NonNull String email);

    // @Query("select distinct u from User u join u.bookings b where
    // b.durationMinutes > :minMinutes")
    // List<OverdueBorrowedItemsData>
    // findUsersWithBookingsExceedingDuration(@Param("minMinutes") Integer
    // minMinutes);
}