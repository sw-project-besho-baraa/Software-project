package Service.NotificationSender;

public interface INotificationSender<TUser, TMessage>
{
    void send(TUser user,TMessage message);
}
