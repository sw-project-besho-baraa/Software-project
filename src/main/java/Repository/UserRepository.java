package Repository;

import Entity.User;

import java.util.List;
import java.util.Optional;

import Service.Book.OverdueBorrowDetection.OverdueBorrowedItemsData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
    Optional<User> findByEmail(String email);

    List<OverdueBorrowedItemsData> findUsersWithBookingsExceedingDuration(int days);
}