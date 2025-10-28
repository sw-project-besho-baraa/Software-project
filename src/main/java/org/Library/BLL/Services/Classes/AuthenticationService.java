package org.Library.BLL.Services.Classes;
import org.Library.BLL.Services.Interfaces.IAuthenticationService;
import org.Library.DAL.DTO.LoginRequest;
import org.Library.DAL.Models.User;
import org.Library.DAL.Repositories.Classes.UserRepository;
import org.Library.DAL.Repositories.Interfaces.IUserRepository;

public class AuthenticationService implements IAuthenticationService {
    IUserRepository users;
    public String errorMassage=null;
    private User current;
    public AuthenticationService(IUserRepository users) {
        this.users = users;
    }

    @Override
    public String login(LoginRequest request){
        if (request==null){
            errorMassage="Invalid request";
            return null;
        }
        User result=users.findUserByUserName(request.getUserName());
        if(result==null){
            errorMassage="User Not Found";
            return null;
        }
        if(!result.getPassword().equals(request.getPassword())){
            errorMassage="Incorrect Password";
            return null;
        }
        if (result.getRole().equals("admin")){
            current=result;
            return result.getRole();
        }
        else {
            current=result;
            return result.getRole();}

    }

    @Override
    public String getErrorMassage() {
        return errorMassage;
    }


    @Override
    public boolean isLoggedIn() {
        return current != null;
    }
    @Override
    public boolean isAdmin() {
        if(current.getRole().equals("admin")){
            return true;
        }
        return false;
    }

    @Override
    public boolean logout() {
        if(!isLoggedIn()){
            errorMassage="You are not logged in";
            return false;
        }
        else {
            current=null;
        return true;
        }
    }

}
