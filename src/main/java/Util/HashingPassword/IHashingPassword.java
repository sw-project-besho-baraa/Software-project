package Util.HashingPassword;

import org.springframework.stereotype.Component;

/**
 * Defines the contract for password hashing and verification.
 * <p>
 * Implementations of this interface should provide secure hashing algorithms
 * (e.g., BCrypt) to protect stored passwords and verify user credentials safely.
 */
@Component
public interface IHashingPassword {

    /**
     * Hashes a plain text password securely.
     *
     * @param password the plain text password to hash
     * @return the hashed password string
     */
    String hashPassword(String password);

    /**
     * Verifies a plain text password against its hashed version.
     *
     * @param password       the plain text password
     * @param hashedPassword the hashed password to compare with
     * @return true if the password matches, false otherwise
     */
    boolean verifyPassword(String password, String hashedPassword);
}
