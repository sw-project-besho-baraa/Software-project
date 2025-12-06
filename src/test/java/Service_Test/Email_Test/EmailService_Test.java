
package Service_Test.Email_Test;

import Service.Email.EmailService;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailService_Test
{
    static class FakeEmailServiceSuccess extends EmailService
    {
        public FakeEmailServiceSuccess(String username, String password)
        {
            super(username, password);
        }

        @Override
        protected void sendMessage(Message message) throws MessagingException
        {

        }
    }

    static class FakeEmailServiceFail extends EmailService
    {
        public FakeEmailServiceFail(String username, String password)
        {
            super(username, password);
        }

        @Override
        protected void sendMessage(Message message) throws MessagingException
        {
            throw new MessagingException("SMTP error");
        }
    }

    static class FakeEmailServiceNull extends EmailService
    {
        public FakeEmailServiceNull(String username, String password)
        {
            super(username, password);
        }

        @Override
        protected void sendMessage(Message message) throws MessagingException
        {
            throw new MessagingException((String) null);
        }
    }

    @Test
    void constructor_DoesNotThrow()
    {
        assertDoesNotThrow(() -> new EmailService("user@gmail.com", "pass"));
    }

    @Test
    void sendEmail_ValidData_ExecutesTryBlockCompletely()
    {
        EmailService service = new FakeEmailServiceSuccess("test@gmail.com", "password");
        assertDoesNotThrow(() -> service.sendEmail("receiver@example.com","Test Subject","<h1>Hello</h1>"));
    }

    @Test
    void sendEmail_WhenSendMessageThrows_ThrowsRuntimeException()
    {
        EmailService service = new FakeEmailServiceFail("test@gmail.com", "password");
        assertThrows(RuntimeException.class,
                () -> service.sendEmail("receiver@example.com","Test Subject","<h1>Hello</h1>"));
    }

    @Test
    void sendEmail_WhenSendMessageThrowsNullMessage_ThrowsRuntimeException()
    {
        EmailService service = new FakeEmailServiceNull("test@gmail.com", "password");
        assertThrows(RuntimeException.class,() -> service.sendEmail("to@test.com","sub","<p>body</p>"));
    }

    @Test
    void sendEmail_WithEmptyRecipient_StillRunsAndDoesNotThrow()
    {
        EmailService service = new FakeEmailServiceSuccess("test@gmail.com", "password");
        assertDoesNotThrow(() -> service.sendEmail("","Empty To","<p>Empty</p>"));
    }

    @Test
    void sendEmail_WithNullRecipient_ThrowsRuntimeException()
    {
        EmailService service = new FakeEmailServiceFail("test@gmail.com", "password");
        assertThrows(Exception.class,() -> service.sendEmail(null,"Null To","<p>Null</p>"));
    }

    @Test
    void sendEmail_WithEmptyBody_ExecutesNormally()
    {
        EmailService service = new FakeEmailServiceSuccess("test@gmail.com", "password");
        assertDoesNotThrow(() -> service.sendEmail("receiver@test.com","Empty Body",""));
    }

    @Test
    void sendEmail_WithLongHtmlBody_ExecutesNormally()
    {
        EmailService service = new FakeEmailServiceSuccess("test@gmail.com", "password");
        String longBody = "<p>" + "Hello " + "</p>";
        assertDoesNotThrow(() -> service.sendEmail("receiver@test.com","Long Body",longBody));
    }
}
