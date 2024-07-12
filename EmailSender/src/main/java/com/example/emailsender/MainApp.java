package com.example.emailsender;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainApp {

    private static final String CSV_FILE = "src/main/resources/recipients.csv";
    private static final String EMAIL_TEMPLATE_FILE = "src/main/resources/email_template.txt";

    public static void main(String[] args) {
        // Load recipients from CSV
        List<String[]> recipients = getRecipientsFromCSV(CSV_FILE);

        if (recipients != null) {
            // Load email template
            String emailTemplate = getEmailTemplate(EMAIL_TEMPLATE_FILE);

            if (emailTemplate != null) {
                // Send emails
                for (String[] recipient : recipients) {
                    String email = recipient[0].trim();
                    String name = recipient[1].trim();

                    // Log the email and name for debugging
                    System.out.println("Sending email to: " + email + ", Name: " + name);

                    // Send email using EmailSender class
                    boolean emailSent = EmailSender.sendEmail(email, name, emailTemplate);

                    if (emailSent) {
                        System.out.println("Email sent successfully to " + email);
                    } else {
                        System.out.println("Failed to send email to " + email);
                    }
                }
            } else {
                System.out.println("Failed to load email template.");
            }
        } else {
            System.out.println("Failed to load recipients from CSV.");
        }
    }

    private static List<String[]> getRecipientsFromCSV(String csvFile) {
        List<String[]> recipients = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 2) {
                    recipients.add(values);
                } else {
                    System.out.println("Invalid line in CSV: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load recipients from CSV. Error message: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        return recipients;
    }

    private static String getEmailTemplate(String templateFile) {
        StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(templateFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Failed to load email template. Error message: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        return contentBuilder.toString();
    }
}
