package com.example.emailsender;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String SMTP_AUTH_USER = "shivansh0539@gmail.com"; // Your email
    private static final String SMTP_AUTH_PWD = "zjwf kyts zfqo cznv";   // Your email password

    public static boolean sendEmail(String toEmail, String name, String emailTemplate) {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_AUTH_USER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Test Email");

            // Replace placeholders in the template
            String emailContent = emailTemplate.replace("{name}", name).replace("{email}", toEmail);
            message.setText(emailContent);

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            System.out.println("Failed to send email to " + toEmail + ". Error message: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
