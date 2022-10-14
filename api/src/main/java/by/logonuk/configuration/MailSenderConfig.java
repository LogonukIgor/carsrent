package by.logonuk.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailSenderConfig {

    @Bean
    public JavaMailSender getMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

//        mailSender.setHost("smtp.yandex.ru");
//        mailSender.setPort(465);
//        mailSender.setUsername("logonukapi@yandex.by");
//        mailSender.setPassword("yzhmtwzcnyczvntc");
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("logonukapi@gmail.com");
        mailSender.setPassword("lgkfcxxhyzgbcbbd");
        mailSender.setProtocol("smtp");

        Properties properties = mailSender.getJavaMailProperties();

        properties.setProperty("mail.transport.protocol", "true");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
//        properties.setProperty("mail.debug", "true");

        return mailSender;
    }
}
