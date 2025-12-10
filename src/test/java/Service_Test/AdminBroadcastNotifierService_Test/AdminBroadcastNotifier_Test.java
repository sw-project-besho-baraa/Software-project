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

import static org.mockito.Mockito.*;

public class AdminBroadcastNotifier_Test
{

    private UserRepository userRepository;
    private INotificationSender<UserContactDTO, String> notifier;
    private AdminBroadcastNotifier broadcastNotifier;

    @BeforeEach
    void setUp()
    {
        userRepository = mock(UserRepository.class);
        notifier = mock(INotificationSender.class);
        broadcastNotifier = new AdminBroadcastNotifier(userRepository, notifier);
    }

    @Test
    void sendToAll_doesNothing_whenMessageIsNull()
    {
        broadcastNotifier.sendToAll(null);
        verifyNoInteractions(userRepository);
        verifyNoInteractions(notifier);
    }

    @Test
    void sendToAll_doesNothing_whenMessageIsBlank()
    {
        broadcastNotifier.sendToAll("   ");
        verifyNoInteractions(userRepository);
        verifyNoInteractions(notifier);
    }

    @Test
    void sendToAll_doesNothing_whenUsersNull()
    {
        when(userRepository.findAll()).thenReturn(null);
        broadcastNotifier.sendToAll("Hello");
        verify(userRepository,times(1)).findAll();
        verifyNoInteractions(notifier);
    }

    @Test
    void sendToAll_doesNothing_whenUsersEmpty()
    {
        when(userRepository.findAll()).thenReturn(List.of());
        broadcastNotifier.sendToAll("Hello");
        verify(userRepository,times(1)).findAll();
        verifyNoInteractions(notifier);
    }

    @Test
    void sendToAll_skipsUsersWithNonUserRole()
    {
        User admin = new User();
        admin.setUserRole(UserRole.Admin);
        admin.setEmail("admin@example.com");
        when(userRepository.findAll()).thenReturn(List.of(admin));

        broadcastNotifier.sendToAll("Hello");
        verifyNoInteractions(notifier);
    }

    @Test
    void sendToAll_skipsUsersWithNullEmail()
    {
        User user = new User();
        user.setUserRole(UserRole.User);
        user.setEmail(null);
        when(userRepository.findAll()).thenReturn(List.of(user));

        broadcastNotifier.sendToAll("Hello");
        verifyNoInteractions(notifier);
    }

    @Test
    void sendToAll_skipsUsersWithBlankEmail()
    {
        User user = new User();
        user.setUserRole(UserRole.User);
        user.setEmail("   ");
        when(userRepository.findAll()).thenReturn(List.of(user));

        broadcastNotifier.sendToAll("Hello");
        verifyNoInteractions(notifier);
    }

    @Test
    void sendToAll_sendsNotification_whenValidUser()
    {
        User user = new User();
        user.setUserRole(UserRole.User);
        user.setName("admin");
        user.setEmail("admin@example.com");
        when(userRepository.findAll()).thenReturn(List.of(user));
        broadcastNotifier.sendToAll("Hello");
        verify(notifier,times(1)).send(any(UserContactDTO.class),eq("Hello"));
    }
}