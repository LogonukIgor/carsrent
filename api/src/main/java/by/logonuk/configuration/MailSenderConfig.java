package by.logonuk.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:mail.properties")
public class MailSenderConfig {

    @Value("${HOST}")
    private String host;

    @Value("${PORT}")
    private Integer port;

    @Value("${USER_NAME}")
    private String userName;

    @Value("${PASSWORD}")
    private String password;

    @Value("${PROTOCOL}")
    private String protocol;
    @Bean
    public JavaMailSender getMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(userName);
        mailSender.setPassword(password);
        mailSender.setProtocol(protocol);

        Properties properties = mailSender.getJavaMailProperties();

        properties.setProperty("mail.transport.protocol", "true");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.debug", "true");

        return mailSender;
    }
}
