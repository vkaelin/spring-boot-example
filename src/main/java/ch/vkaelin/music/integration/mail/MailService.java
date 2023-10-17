package ch.vkaelin.music.integration.mail;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

@Slf4j
public abstract class MailService {
    public abstract Properties getProperties();

    public abstract Session getSession();

    public void sendMail(String from, String to, String subject, String content) {
        Session session = getSession();
        Message message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(content, "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);

            Transport.send(message);
        } catch (MessagingException e) {
            log.error("Error sending mail", e);
        }
    }
}
