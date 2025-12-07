package DTO.UserDTO;

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
    private String phoneNumber;
}
