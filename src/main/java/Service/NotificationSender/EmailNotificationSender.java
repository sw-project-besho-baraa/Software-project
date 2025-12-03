package Service.NotificationSender;

import DTO.UserDTO.UserContactDTO;

public class EmailNotificationSender<TMessage> implements INotificationSender<UserContactDTO, TMessage>
{
    @Override
    public void send(UserContactDTO user,TMessage message)
    {

    }
}
