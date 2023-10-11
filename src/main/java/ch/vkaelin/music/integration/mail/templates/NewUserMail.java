package ch.vkaelin.music.integration.mail.templates;

import ch.vkaelin.music.integration.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewUserMail {
    private final MailService mailService;

    public void sendMail(String username) {
        String from = "no-reply@music-library.swiss";
        String to = "info@music-library.swiss";
        String subject = "New user registered";
        String content = "New user " + username + " registered.";

        mailService.sendMail(from, to, subject, content);
    }
}
