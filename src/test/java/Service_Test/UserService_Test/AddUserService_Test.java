package Service_Test.UserService_Test;

import Entity.User;
import Enum.UserRole;
import Repository.UserRepository;
import Service.UserService.AddUserService;
import Util.HashingPassword.BCryptHashingPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AddUserService_Test
{

    private UserRepository userRepository;
    private BCryptHashingPassword hashingPassword;
    private AddUserService addUserService;

    @BeforeEach
    void setUp()
    {
        userRepository = mock(UserRepository.class);
        hashingPassword = mock(BCryptHashingPassword.class);
        addUserService = new AddUserService(userRepository, hashingPassword);
    }

    @Test
    void addUser_hashesPasswordAndSavesUser()
    {
        String name = "Mohammad";
        String email = "mohammad@example.com";
        String password = "1234";
        String hashedPassword = "hashed1234";
        UserRole role = UserRole.User;
        when(hashingPassword.hashPassword(password)).thenReturn(hashedPassword);
        addUserService.addUser(name,email,password,role);
        verify(hashingPassword,times(1)).hashPassword(password);
        verify(userRepository,times(1))
                .save(argThat(user -> user.getName().equals(name) && user.getEmail().equals(email)
                        && user.getHashedPassword().equals(hashedPassword) && user.getUserRole() == role));
    }

    @Test
    void addUser_doesNotThrowException()
    {
        when(hashingPassword.hashPassword(anyString())).thenReturn("hash");
        assertDoesNotThrow(() -> addUserService.addUser("A","a@mail.com","pass",UserRole.User));
    }
}
