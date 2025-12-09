package Service_Test.AdminBroadcastNotifierService_Test;

import DTO.UserDTO.UserContactDTO;
import Entity.User;
import Enum.UserRole;
import Repository.UserRepository;
import Service.AdminBroadcastNotifierService.AdminBroadcastNotifier;
import Service.NotificationSender.INotificationSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

public class AdminBroadcastNotifier_Test
{

    private UserRepository userRepository;
    private INotificationSender<UserContactDTO, String> notifier;
    private AdminBroadcastNotifier service;

    @BeforeEach
    void setup()
    {
        userRepository = mock(UserRepository.class);
        notifier = mock(INotificationSender.class);
        service = new AdminBroadcastNotifier(userRepository, notifier);
    }

    @Test
    void sendToAll_nullOrBlankMessage_doesNothing()
    {
        service.sendToAll(null);
        service.sendToAll("   ");
        verifyNoInteractions(userRepository,notifier);
    }

    @Test
    void sendToAll_usersNullOrEmpty_doesNotSend()
    {
        when(userRepository.findAll()).thenReturn(null);
        service.sendToAll("msg");
        when(userRepository.findAll()).thenReturn(List.of());
        service.sendToAll("msg");
        verify(userRepository,times(2)).findAll();
        verifyNoInteractions(notifier);
    }

    @Test
    void sendToAll_onlyUserRoleWithEmail_getsNotified()
    {
        User admin = mock(User.class);
        when(admin.getUserRole()).thenReturn(UserRole.Admin);
        User userNoEmail = mock(User.class);
        when(userNoEmail.getUserRole()).thenReturn(UserRole.User);
        when(userNoEmail.getEmail()).thenReturn(" ");
        User goodUser = mock(User.class);
        when(goodUser.getUserRole()).thenReturn(UserRole.User);
        when(goodUser.getEmail()).thenReturn("user@example.com");
        when(goodUser.getName()).thenReturn("Mohammad");
        when(userRepository.findAll()).thenReturn(List.of(admin,userNoEmail,goodUser));
        String msg = "Hello";
        service.sendToAll(msg);
        verify(notifier).send(argThat(c -> "Mohammad".equals(c.getName()) && "user@example.com".equals(c.getEmail())),
                eq(msg));
        verifyNoMoreInteractions(notifier);
    }
}