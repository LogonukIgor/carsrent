package by.logonuk.service.mailsender;

import by.logonuk.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final MailSender mailSender;

    public void sendEmail(String toEmail, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("logonukapi@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendMailToUserActivator(User user) {
        sendEmail(
                user.getCredentials().getMail(),
                "Mail confirmation",
                String.format(
                        "Follow the link to confirm: http://localhost:8080/users/activation?code=%s",
                        user.getActivationCode()));
    }
}
