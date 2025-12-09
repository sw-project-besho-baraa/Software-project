package DTO.UserDTO;

import Entity.User;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class UserContactDTO
{
    private int id;
    private String name;
    private String email;
    public UserContactDTO(){}
    public UserContactDTO(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
