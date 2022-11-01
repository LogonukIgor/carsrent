package by.logonuk.service.mailsender;

import by.logonuk.domain.Deal;
import by.logonuk.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:mail.properties")
public class MailSenderService {
    private final MailSender mailSender;

    @Value("${YOUR_HOST}")
    private String yourHost;

    public void sendEmail(String toEmail, String subject, String body) {
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
                        "Follow the link to confirm:\nhttp://%s/users/activation?code=%s",
                        yourHost,
                        user.getActivationCode()));
    }

    public void sendDealDetailsToUser(Deal deal) {
        sendEmail(
                deal.getUser().getCredentials().getMail(),
                "Your order",
                String.format(
                        "Order details:\n\n"
                                + "--------------------------------------"
                                + "1) car - %s;\n"
                                + "2)price - %d$;\n"
                                + "3)date: %s;\n\n"
                                + "--------------------------------------"
                                + "Order details can be specified by our manager. Thank you for choosing our company:)",
                        deal.getCar().getCarInfo().getCarManufacture().getBrand() + " " +
                                deal.getCar().getCarInfo().getModel().getModelName(),
                        deal.getPrice().intValue(),
                        deal.getReceivingDate().toString() + " - " + deal.getReturnDate().toString()
                ));
    }
}
