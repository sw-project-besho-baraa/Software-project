package Fine;
import org.Library.BLL.Services.Classes.FineService;
import org.Library.BLL.Services.Classes.UserService;
import org.Library.BLL.Services.Interfaces.IFineService;
import org.Library.BLL.Services.Interfaces.IUserService;
import org.Library.DAL.Models.User;
import org.Library.DAL.Repositories.Classes.UserRepository;
import org.Library.DAL.Repositories.Interfaces.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class FinePay {
    IUserRepository userRepo=new UserRepository();
    IUserService userService=new UserService(userRepo);
    IFineService fineService=new FineService(userService);



    @BeforeEach
    void setup(){
        User user = new User();
        user.setUserName("llll");
        user.setPassword("1234");
        user.setRole("user");
        user.setFineAmount(40);
        User user1 = new User();
        user1.setUserName("ll");
        user1.setPassword("1234");
        user1.setRole("user");
        user1.setFineAmount(40);
        userRepo.saveUser(user1);
        userRepo.saveUser(user);
    }


    @Test
    void finePayUserNotFound() {
        int fine = fineService.payFineByUserName("i",20);
        assertEquals("User not found", fineService.getErrorMessage());
    }

    @Test
    void finePayUserNameNull() {
        int fine = fineService.payFineByUserName(null,20);
        assertEquals("Name is null or empty", fineService.getErrorMessage());
    }

    @Test
    void finePayUserNameEmpty() {
        int fine = fineService.payFineByUserName("",20);
        assertEquals("Name is null or empty", fineService.getErrorMessage());
    }

    @Test
    void finePayAmountEqual0() {
        int fine = fineService.payFineByUserName("llll",0);
        assertEquals("No money", fineService.getErrorMessage());
    }

    @Test
    void finePayAmountLessThanZero() {
        int fine = fineService.payFineByUserName("llll",-2);
        assertEquals("Unacceptable Amount", fineService.getErrorMessage());
    }
    @Test
    void finePayPartOfFineAmount() {
        int fine = fineService.payFineByUserName("llll",20);
        assertEquals(20,fine);
    }
    @Test
    void finePayFineAmount() {
        int fine = fineService.payFineByUserName("ll",40);
        assertEquals(0,fine);
    }

}
