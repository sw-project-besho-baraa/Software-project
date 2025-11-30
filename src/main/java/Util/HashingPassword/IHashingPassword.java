package Util.HashingPassword;

public interface IHashingPassword
{

    public String hashPassword(String password);

    public boolean verifyPassword(String password,String hashedPassword);
}
