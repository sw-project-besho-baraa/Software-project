package org.Library.DAL.Repositories.Classes;

import org.Library.DAL.DTO.LoginRequest;
import org.Library.DAL.Data.Users;
import org.Library.DAL.Models.User;
import org.Library.DAL.Repositories.Interfaces.IUserRepository;
import static org.Library.DAL.Data.Users.UsersList;

public class UserRepository implements IUserRepository {

    @Override
    public User findUserByUserName(String userName) {
        User user=new User();
        boolean Found=false;
        for(User u :UsersList){
            if (u.userName.equals(userName)){
                user=u;
                Found=true;
                break;

            }
        }

        return (Found)?user:null;
    }

    @Override
    public void saveUser(User user) {
        UsersList.add(user);
    }

    @Override
    public int getUserFineAmount(User user) {
        return user.getFineAmount();
    }

    @Override
    public void setUserFineAmount(User user, int userFineAmount) {
        user.setFineAmount(userFineAmount);

    }


}
