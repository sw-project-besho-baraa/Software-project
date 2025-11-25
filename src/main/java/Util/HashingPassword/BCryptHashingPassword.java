package Util.HashingPassword;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptHashingPassword implements IHashingPassword {
    int bara;
    @Override
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    @Override
    public boolean verifyPassword(String password, String hashedPassword) {
        return  BCrypt.checkpw(password, hashedPassword);
    }
}
