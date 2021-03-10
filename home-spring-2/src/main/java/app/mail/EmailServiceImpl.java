package app.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private static final String NOREPLY_ADDRESS = "noreply@salat.com";

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(NOREPLY_ADDRESS);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void sendMimeMessage(String to, String subject, String text) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            String htmlMsg = text;
            helper.setText(htmlMsg, true); // Use this or above line.
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(NOREPLY_ADDRESS);
            emailSender.send(mimeMessage);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
