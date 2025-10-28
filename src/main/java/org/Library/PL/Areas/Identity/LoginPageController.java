package org.Library.PL.Areas.Identity;
import org.Library.BLL.Services.Classes.AuthenticationService;
import org.Library.BLL.Services.Interfaces.IAuthenticationService;
import org.Library.DAL.DTO.LoginRequest;

public class LoginPageController {
    private IAuthenticationService authService;
    public LoginPageController(IAuthenticationService authService) {
        this.authService = authService;
    }

    public String Login(String username, String password) {
        String success = authService.login(new LoginRequest(username, password));
        return success;
    }
}
