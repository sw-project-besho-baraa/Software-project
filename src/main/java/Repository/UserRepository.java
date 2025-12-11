package Repository;

import Entity.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import Enum.UserRole;
import java.util.Optional;

/**
 * Repository for managing {@link User} entities.
 * <p>
 * Provides methods for authentication, role counting, and lazy loading of borrowed items.
 */
@Repository
public interface UserRepository extends JpaRepository<@NonNull User, @NonNull Integer> {

    /**
     * Finds a user by their email address.
     *
     * @param email user email
     * @return optional user if found
     */
    Optional<User> findByEmail(@NonNull String email);

    /**
     * Counts users by their assigned role.
     *
     * @param userRole user role
     * @return number of users with the given role
     */
    long countByUserRole(@NonNull UserRole userRole);

    /**
     * Finds a user and fetches their borrowed items in a single query.
     *
     * @param id user ID
     * @return optional user with borrowed items loaded
     */
    @Query("select u from User u left join fetch u.borrowedItems where u.id = :id")
    Optional<User> findByIdWithBorrowedItems(@Param("id") Integer id);
}
