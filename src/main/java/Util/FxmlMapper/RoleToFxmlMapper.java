package Util.FxmlMapper;

import Enum.UserRole;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * Maps user roles to their corresponding FXML view files.
 * <p>
 * This utility is used to determine which UI screen should be loaded after a
 * successful login based on the user's role.
 */
@Component
public class RoleToFxmlMapper
{

    private final Map<UserRole, String> roleToFxmlMap;

    /**
     * Initializes the role-to-FXML mapping.
     */
    public RoleToFxmlMapper()
    {
        roleToFxmlMap = Map.of(UserRole.Admin,"/fxml/admin.fxml",UserRole.User,"/fxml/dash.fxml",UserRole.Librarian,
                "/fxml/librarian.fxml");
    }

    /**
     * Retrieves the FXML file path corresponding to the given user role.
     *
     * @param role
     *            the {@link UserRole} of the user
     * @return an {@link Optional} containing the FXML file path if available
     */
    public Optional<String> getFxmlFileForRole(UserRole role)
    {
        System.out.println(roleToFxmlMap.get(role));
        return Optional.ofNullable(roleToFxmlMap.get(role));
    }
}
