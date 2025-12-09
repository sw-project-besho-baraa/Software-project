package Service_Test.NotificationSender_Test;

import Service.NotificationSender.INotificationSender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class INotificationSender_Test
{

    static class DummySender implements INotificationSender<String, String>
    {
        boolean called = false;
        @Override
        public void send(String user,String msg)
        {
            called = true;
        }
    }

    @Test
    void send_invokedSuccessfully()
    {
        DummySender sender = new DummySender();
        sender.send("mobo","hi");
        assertTrue(sender.called);
    }
}