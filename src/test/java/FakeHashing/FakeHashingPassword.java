package FakeHashing;

import Util.HashingPassword.IHashingPassword;

public class FakeHashingPassword implements IHashingPassword {

    @Override
    public String hashPassword(String password) {
        return "";
    }

    @Override
    public boolean verifyPassword(String password, String hashedPassword) {
        return false;
    }
}