package Service.NotificationSender;

public interface INotificationSender<TUser, TMessage>
{
    public void send(TUser user,TMessage message);
}
