package Service.NotificationSender;

/**
 * Generic interface for sending notifications to users.
 *
 * @param <TUser>
 *            the type representing the user or recipient
 * @param <TMessage>
 *            the type representing the message or data to send
 */
public interface INotificationSender<TUser, TMessage>
{

    /**
     * Sends a message or notification to a specific user.
     *
     * @param user
     *            recipient of the notification
     * @param message
     *            content or data of the message
     */
    void send(TUser user,TMessage message);
}