package org.Library.DAL.Repositories.Interfaces;

import org.Library.DAL.DTO.LoginRequest;
import org.Library.DAL.Models.User;

public interface IUserRepository {
    public User findUserByUserName(String userName);
    public void saveUser(User user);
    public int getUserFineAmount(User user);
    public void setUserFineAmount(User user, int userFineAmount);

}
