package Util_Test.FxmlMapper;

import Enum.UserRole;
import Util.FxmlMapper.RoleToFxmlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class RoleToFxmlMapper_Test
{
    private RoleToFxmlMapper mapper;

    @BeforeEach
    void setup()
    {
        mapper = new RoleToFxmlMapper();
    }

    @Test
    void getFxmlFileForRole_Admin_returnsAdminPath()
    {
        Optional<String> result = mapper.getFxmlFileForRole(UserRole.Admin);
        assertTrue(result.isPresent());
        assertEquals("/fxml/admin.fxml",result.get());
    }

    @Test
    void getFxmlFileForRole_User_returnsUserPath()
    {
        Optional<String> result = mapper.getFxmlFileForRole(UserRole.User);
        assertTrue(result.isPresent());
        assertEquals("/fxml/dash.fxml",result.get());
    }

    @Test
    void getFxmlFileForRole_Librarian_returnsLibrarianPath()
    {
        Optional<String> result = mapper.getFxmlFileForRole(UserRole.Librarian);
        assertTrue(result.isPresent());
        assertEquals("/fxml/librarian.fxml",result.get());
    }

    @Test
    void getFxmlFileForRole_nullRole_throwsNullPointerException()
    {
        assertThrows(NullPointerException.class,() -> mapper.getFxmlFileForRole(null));
    }
}
