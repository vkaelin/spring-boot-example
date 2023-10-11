package ch.vkaelin.music.integration.mail;

import jakarta.mail.Session;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class LocalMailService extends MailService {
    @Override
    public Properties getProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", false);
        prop.put("mail.smtp.host", "localhost");
        prop.put("mail.smtp.port", "2525");

        return prop;
    }

    @Override
    public Session getSession() {
        return Session.getInstance(getProperties(), null);
    }
}
