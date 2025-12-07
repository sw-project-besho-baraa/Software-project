package Util.FxmlMapper;
import Enum.UserRole;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class RoleToFxmlMapper {
    private final Map<UserRole, String> roleToFxmlMap;
    public RoleToFxmlMapper() {
        roleToFxmlMap = Map.of(
                UserRole.Admin, "/fxml/admin.fxml",
                UserRole.User, "/fxml/dash.fxml",
                UserRole.Librarian, "/fxml/librarian.fxml"

        );
    }
    public Optional<String> getFxmlFileForRole(UserRole role) {
        System.out.println(roleToFxmlMap.get(role));
        return Optional.ofNullable(roleToFxmlMap.get(role));

    }
}