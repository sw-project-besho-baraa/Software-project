package Util_Test.MessageFormater;

import DTO.UserDTO.UserContactDTO;
import Service.BroadcastMessage.AdminBroadcastMessageData;
import Util.MessageFormater.AdminBroadcastMessageFormater;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminBroadcastMessageFormater_Test
{

    AdminBroadcastMessageFormater f = new AdminBroadcastMessageFormater();

    @Test
    void nullData_returnsEmpty()
    {
        assertEquals("",f.formatMessage(null));
    }

    @Test
    void nullUser_returnsEmpty()
    {
        var d = mock(AdminBroadcastMessageData.class);
        when(d.userContactDTO()).thenReturn(null);
        assertEquals("",f.formatMessage(d));
    }

    @Test
    void fullData_replacesAndEscapes()
    {
        var u = mock(UserContactDTO.class);
        when(u.getName()).thenReturn("Mohammad");
        var d = mock(AdminBroadcastMessageData.class);
        when(d.userContactDTO()).thenReturn(u);
        when(d.message()).thenReturn("Hi & <you>\nline");
        var r = f.formatMessage(d);
        assertTrue(r.contains("Mohammad"));
        assertTrue(r.contains("&amp;"));
        assertTrue(r.contains("<br/>"));
        assertFalse(r.contains("__USER_NAME__"));
    }

    @Test
    void blankNameAndNullMessage_defaultAndNoNull()
    {
        var u = mock(UserContactDTO.class);
        when(u.getName()).thenReturn(" ");
        var d = mock(AdminBroadcastMessageData.class);
        when(d.userContactDTO()).thenReturn(u);
        when(d.message()).thenReturn(null);
        var r = f.formatMessage(d);
        assertTrue(r.contains("Valued Reader"));
        assertFalse(r.contains("null"));
    }
}