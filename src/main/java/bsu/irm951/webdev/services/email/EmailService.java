package bsu.irm951.webdev.services.email;

import bsu.irm951.webdev.services.email.messages.ConfirmationTokenEmail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private ConfirmationTokenEmail emailMessage;
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void sendEmail(String recipientAddress, String token){
        emailMessage = new ConfirmationTokenEmail();
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessage.setContent(token, "text/html");
            helper.setTo(recipientAddress);
            helper.setSubject("Confirm your identity at anything :)");
            helper.setFrom("anythingdevteam@gmail.com");
            mailSender.send(mimeMessage);
        } catch(MessagingException e){
            e.printStackTrace();
            throw new IllegalStateException("failed to send email");
        }
    }
}
