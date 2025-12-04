package DTO.UserDTO;

import lombok.*;

/**
 * Data Transfer Object representing credentials supplied by a user during
 * authentication.
 * <p>
 * This DTO typically contains the minimal information required to attempt a
 * login.
 */
@Getter
@Setter
public class UserCredentialsDTO
{
    private String email;
    private String password;

    /**
     * Creates a new UserDTO with the provided credentials.
     *
     * @param email
     *            the user's email address
     * @param password
     *            the user's password (usually a plaintext input that will be
     *            validated/hashed elsewhere)
     */
    public UserCredentialsDTO(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

}
