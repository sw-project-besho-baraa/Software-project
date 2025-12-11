package DTO.UserDTO;

import lombok.*;

/**
 * DTO for user login credentials (email and password).
 */
@Getter
@Setter
public class UserCredentialsDTO
{

    private String email;
    private String password;

    /**
     * Constructor to set user credentials.
     *
     * @param email
     *            user email
     * @param password
     *            user password
     */
    public UserCredentialsDTO(String email, String password)
    {
        this.email = email;
        this.password = password;
    }
}
