package Service_Test.BroadcastMessage_Test;

import DTO.UserDTO.UserContactDTO;
import Service.BroadcastMessage.AdminBroadcastMessageData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdminBroadcastMessageData_Test
{

    @Test
    void record_storesAndReturnsValues()
    {
        UserContactDTO user = new UserContactDTO();
        user.setName("bara");
        user.setEmail("bara@gmail.com");
        AdminBroadcastMessageData data = new AdminBroadcastMessageData(user, "bara");
        assertEquals(user,data.userContactDTO());
        assertEquals("bara",data.message());
    }
}