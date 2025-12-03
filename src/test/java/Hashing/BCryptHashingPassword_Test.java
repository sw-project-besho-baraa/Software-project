package Hashing;

import Util.HashingPassword.BCryptHashingPassword;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class BCryptHashingPassword_Test
{
    private BCryptHashingPassword bCryptHashingPassword = new BCryptHashingPassword();

    @Test
    void hashPassword_ShouldReturnDifferentFromNonHashedPassword()
    {
        String password = "password";
        String hashedPassword = bCryptHashingPassword.hashPassword(password);
        assertNotEquals(hashedPassword,password);
    }

    @Test
    void hashPassword_ShouldReturnNotNullHashedPassword()
    {
        String password = "password";
        String hashedPassword = bCryptHashingPassword.hashPassword(password);
        assertNotNull(hashedPassword);
    }

    @Test
    void verifyPassword_WithCorrectPassword_ReturnsTrue()
    {
        String password = "password";
        String hashedPassword = bCryptHashingPassword.hashPassword(password);
        boolean result = bCryptHashingPassword.verifyPassword(password,hashedPassword);
        assertTrue(result);
    }

    @Test
    void verifyPassword_WithInvalidCorrectPassword_ReturnsFalse()
    {
        String password = "password";
        String hashedPassword = bCryptHashingPassword.hashPassword(password);
        boolean result = bCryptHashingPassword.verifyPassword("Wrong Password",hashedPassword);
        assertFalse(result);
    }

    @Test
    void hashPassword_ShouldReturnDifferentHashedPasswordEachTime_ReturnsDifferentHashedPassword()
    {
        String password = "password";
        String hashedPassword = bCryptHashingPassword.hashPassword(password);
        String hashedPassword2 = bCryptHashingPassword.hashPassword(password);
        assertNotEquals(hashedPassword,hashedPassword2);
    }

}
