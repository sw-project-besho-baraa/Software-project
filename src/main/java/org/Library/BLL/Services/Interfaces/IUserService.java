package org.Library.BLL.Services.Interfaces;

import org.Library.DAL.Models.User;

public interface IUserService {
    User findUserByUserName(String userName);
    public int getFineAmount (User user );
    public void setFineAmount(User user, int amount);
}
