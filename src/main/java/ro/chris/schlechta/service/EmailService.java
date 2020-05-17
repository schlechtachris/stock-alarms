package ro.chris.schlechta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.chris.schlechta.model.persisted.StockAlarm;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Value("${email}")
    private String email;

    @Value("${email.password}")
    private String password;

    /**
     * sends email for the user who set the alarm for the given stock
     *
     * @param stockAlarm alarm set by the user for the given stock
     */
    public void sendEmail(StockAlarm stockAlarm) {
        LOGGER.info("Sending email to user {}...", stockAlarm.getUser().getEmail());

        Properties sessionProperties = setupMailSessionProperties();
        Session session = Session.getInstance(sessionProperties, createAuthenticator());

        try {
            Message message = buildEmailMessage(session, stockAlarm);
            Transport.send(message);

            LOGGER.info("The email was send successfully.");
        } catch (MessagingException e) {
            LOGGER.error("There was a problem send email to user {}!", stockAlarm.getUser().getEmail());
            e.printStackTrace();
        }

    }

    private Message buildEmailMessage(Session session, StockAlarm stockAlarm) throws MessagingException {
        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress(email));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(stockAlarm.getUser().getEmail())
        );
        message.setSubject(String.format("Alert for stock %s!", stockAlarm.getStockSymbol()));
        message.setText(composeEmailText(stockAlarm));

        return message;
    }

    private String composeEmailText(StockAlarm stockAlarm) {
        return String.format(
                "The price of the stock %s has changed. The original price was $%,.2f but the current price is $%,.2f.",
                stockAlarm.getStockSymbol(),
                stockAlarm.getInitialPrice(),
                stockAlarm.getCurrentPrice()
        );
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
