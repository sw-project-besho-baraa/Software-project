package org.Library.BLL.Services.Classes;

import org.Library.BLL.Services.Interfaces.IFineService;
import org.Library.BLL.Services.Interfaces.IUserService;
import org.Library.DAL.Models.User;
import org.Library.DAL.Repositories.Classes.UserRepository;
import org.Library.DAL.Repositories.Interfaces.IUserRepository;

public class FineService implements IFineService {
    private IUserService userService;
    private String errorMassage;
    public FineService (IUserService userService ) {
        this.userService = userService;
    }

    @Override
    public int payFineByUserName(String name, int amount) {
        if (name == null || name.isEmpty()){
            errorMassage = "Name is null or empty";
            return -1;
        }
       User n =userService.findUserByUserName(name);
       if (n==null){
           errorMassage = "User not found";
           return -1;

       }
       if (amount==0){
           errorMassage = "No money";
           return -1;
       }
       if (amount<0) {
           errorMassage = "Unacceptable Amount";
           return -1;
       }
       if(userService.getFineAmount(n)>amount){
           int fineRem=userService.getFineAmount(n)-amount;
           userService.setFineAmount(n,fineRem);
           return fineRem;
       }
       else {
           userService.setFineAmount(n,0);
           return 0;
       }


    }

    @Override
    public String getErrorMessage() {
        return errorMassage;
    }
}
