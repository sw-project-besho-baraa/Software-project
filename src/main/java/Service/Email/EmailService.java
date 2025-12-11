package Service.Email;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Properties;

/**
 * Handles sending emails using SMTP (Gmail).
 */
@Component
public class EmailService {

    private final String username;
    private final String password;

    /**
     * Creates an email service with provided credentials.
     *
     * @param username SMTP username (email address)
     * @param password SMTP password or app-specific key
     */
    public EmailService(@Value("${app.mail.username}") String username,
                        @Value("${app.mail.password}") String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Sends a prepared email message through the mail transport.
     *
     * @param message message to send
     * @throws MessagingException if sending fails
     */
    protected void sendMessage(Message message) throws MessagingException {
        Transport.send(message);
    }

    /**
     * Composes and sends an HTML email to the specified recipient.
     *
     * @param to recipient email address
     * @param subject email subject
     * @param bodyHtml HTML content of the email
     */
    public void sendEmail(String to, String subject, String bodyHtml) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(bodyHtml, "text/html; charset=UTF-8");
            sendMessage(message);
            System.out.println("Email sent successfully to " + to);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
