package Util.HashingPassword;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * Provides password hashing and verification using the BCrypt algorithm.
 * <p>
 * This class implements {@link IHashingPassword} and is used for securely
 * storing and validating user passwords.
 */
@Component
public class BCryptHashingPassword implements IHashingPassword {

    /**
     * Hashes a plain text password using BCrypt with a strength of 12.
     *
     * @param password the plain text password
     * @return the hashed password
     * @see BCrypt#hashpw(String, String)
     */
    @Override
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    /**
     * Verifies a password against a previously hashed value.
     *
     * @param password       the plain text password
     * @param hashedPassword the hashed password to compare with
     * @return true if the password matches, false otherwise
     * @see BCrypt#checkpw(String, String)
     */
    @Override
    public boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}

