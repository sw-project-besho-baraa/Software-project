package DTO;

/**
 * Data Transfer Object representing credentials supplied by a user during authentication.
 * <p>
 * This DTO typically contains the minimal information required to attempt a login.
 */
public class UserDTO
{
    private String email;
    private String password;

    /**
     * Creates a new UserDTO with the provided credentials.
     *
     * @param email    the user's email address
     * @param password the user's password (usually a plaintext input that will be validated/hashed elsewhere)
     */
    public UserDTO(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    /**
     * Returns the user's email address.
     *
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email the email to set
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Returns the user's password as provided in the login form.
     *
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the password to set
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
}
