package org.Library.BLL.Services.Classes;

import org.Library.BLL.Services.Interfaces.IUserService;
import org.Library.DAL.Models.User;
import org.Library.DAL.Repositories.Interfaces.IUserRepository;

public class UserService implements IUserService {
    IUserRepository userRepository;
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User findUserByUserName(String userName) {
        return userRepository.findUserByUserName(userName);
    }

    @Override
    public int getFineAmount(User user) {
        return userRepository.getUserFineAmount(user);
    }

    @Override
    public void setFineAmount(User user, int amount) {
        userRepository.setUserFineAmount(user, amount);
    }

}
