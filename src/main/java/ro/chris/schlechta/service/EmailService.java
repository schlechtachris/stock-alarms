package ro.chris.schlechta.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.chris.schlechta.model.StockAlarm;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {

    @Value("${email}")
    private String email;

    @Value("${email.password}")
    private String password;

    public void sendEmail(String userEmail, StockAlarm stockAlarm) {

        Properties sessionProperties = setupMailSessionProperties();

        Session session = Session.getInstance(sessionProperties, createAuthenticator());

        try {
            Message message = buildEmailMessage(session, null, null);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Message buildEmailMessage(Session session, String userEmail, StockAlarm stockAlarm) throws MessagingException {
        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress(email));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(userEmail)
        );
        //TODO set subject & text of the email
        message.setSubject("");
        message.setText("");

        return message;
    }

    private Properties setupMailSessionProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return prop;
    }

    private Authenticator createAuthenticator() {
        return new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        };
    }

}
