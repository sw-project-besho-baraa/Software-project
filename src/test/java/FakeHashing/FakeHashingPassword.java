package FakeHashing;

import Util.HashingPassword.IHashingPassword;

public class FakeHashingPassword implements IHashingPassword {

    private boolean called = false;
    private boolean resultToReturn = true;
    private String lastPlainPassword;
    private String lastHashedPassword;

    @Override
    public String hashPassword(String password) {
        return password + "_hashed";
    }

    @Override
    public boolean verifyPassword(String password, String hashedPassword) {
        called = true;
        lastPlainPassword = password;
        lastHashedPassword = hashedPassword;
        return resultToReturn;
    }

    public boolean wasCalled() {
        return called;
    }

    public void setResultToReturn(boolean resultToReturn) {
        this.resultToReturn = resultToReturn;
    }

    public String getLastPlainPassword() {
        return lastPlainPassword;
    }

    public String getLastHashedPassword() {
        return lastHashedPassword;
    }

    public void reset() {
        called = false;
        lastPlainPassword = null;
        lastHashedPassword = null;
    }
}
