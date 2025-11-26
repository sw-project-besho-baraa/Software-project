package FakeValidation;

import DTO.UserDTO;
import Entity.User;
import Util.HashingPassword.IHashingPassword;
import Validation.LoginCredentialsValidator;

/**
 * Fake implementation of LoginCredentialsValidator used for unit testing
 * without relying on real hashing or validation logic.
 *
 * Instead of actually verifying passwords, it returns a predefined result.
 * It also records whether it was called, and with which parameters.
 */
public class FakeLoginCredentialsValidator extends LoginCredentialsValidator {


    private boolean wasCalled = false;
    private boolean resultToReturn = true;
    private UserDTO lastDto = null;
    private User lastUser = null;

    /**
     * Constructor passes fake hashing instance to parent class.
     */
    public FakeLoginCredentialsValidator(IHashingPassword hashingPassword) {
        super(hashingPassword);
    }

    /**
     * Fake validation logic:
     *  Records that it was called.
     *  Stores the passed DTO and User.
     *  Returns a pre-defined result (controlled from tests).
     */
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
