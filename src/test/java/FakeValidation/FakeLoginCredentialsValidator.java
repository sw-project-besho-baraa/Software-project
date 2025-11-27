package FakeValidation;

import DTO.UserDTO;
import Entity.User;
import Util.HashingPassword.IHashingPassword;
import Validation.LoginCredentialsValidator;


public class FakeLoginCredentialsValidator extends LoginCredentialsValidator {


    private boolean wasCalled = false;
    private boolean resultToReturn = true;
    private UserDTO lastDto = null;
    private User lastUser = null;

    public FakeLoginCredentialsValidator(IHashingPassword hashingPassword) {
        super(hashingPassword);
    }


    @Override
    public boolean validateLogin(UserDTO dto, User user) {
        wasCalled = true;
        lastDto = dto;
        lastUser = user;
        return resultToReturn;
    }

    public boolean wasCalled() {
        return wasCalled;
    }

    public UserDTO getLastDto() {
        return lastDto;
    }

    public User getLastUser() {
        return lastUser;
    }

    public void setResultToReturn(boolean resultToReturn) {
        this.resultToReturn = resultToReturn;
    }

    public void reset() {
        wasCalled = false;
        lastDto = null;
        lastUser = null;
    }
}
