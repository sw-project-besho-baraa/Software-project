package Util.HashingPassword;

import org.springframework.stereotype.Component;

@Component
public interface IHashingPassword
{

    public String hashPassword(String password);

    public boolean verifyPassword(String password,String hashedPassword);
}
