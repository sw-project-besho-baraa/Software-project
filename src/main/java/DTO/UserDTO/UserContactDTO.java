package DTO.UserDTO;

import Entity.User;
import lombok.*;

/**
 * Simple DTO for transferring basic user contact info (id, name, email).
 */
@Getter
@Setter
public class UserContactDTO
{

    private int id;
    private String name;
    private String email;

    /**
     * Default constructor.
     */
    public UserContactDTO()
    {
    }

    /**
     * Create DTO from a User entity.
     *
     * @param user
     *            the user to map
     */
    public UserContactDTO(User user)
    {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
