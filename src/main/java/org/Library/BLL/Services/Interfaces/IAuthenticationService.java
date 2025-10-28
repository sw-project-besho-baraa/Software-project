package org.Library.BLL.Services.Interfaces;

import org.Library.DAL.DTO.LoginRequest;
import org.Library.DAL.Models.User;


public interface IAuthenticationService {
      public String login(LoginRequest request);
      public String getErrorMassage();
      boolean isLoggedIn();
      boolean isAdmin();
      public boolean logout();
}
