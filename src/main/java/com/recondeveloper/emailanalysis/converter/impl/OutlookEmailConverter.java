package com.recondeveloper.emailanalysis.converter.impl;

import com.recondeveloper.emailanalysis.converter.EmailConverter;
import com.recondeveloper.emailanalysis.model.Email;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.apache.poi.hsmf.MAPIMessage;

public class OutlookEmailConverter implements EmailConverter {


    private MimeMessage readPstFile(String filePath) {
        throw new RuntimeException("not implemented");
    }
    private MAPIMessage readMsgFile(String filePath) {
        try {
            return new MAPIMessage(filePath);
        } catch (IOException e) {
            e.printStackTrace(); // Consider using a logger instead
            return null;
        }
    }
    private MimeMessage readEmlFile(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            Session session = Session.getDefaultInstance(new Properties());
            return new MimeMessage(session, fis);
        } catch (MessagingException | IOException e) {
            e.printStackTrace(); // Consider using a logger instead
            return null;
        }
    }

    private String extractEmailAddress(String emailString) {
        int start = emailString.indexOf('<');
        int end = emailString.indexOf('>');
        if (start != -1 && end != -1) {
            return emailString.substring(start + 1, end);
        }
        return emailString;
    }
    private void processEmlHeaders(MimeMessage message, Email email) throws MessagingException {
        // Common headers
        email.setSubject(message.getSubject());
        email.setSender(extractEmailAddress(Arrays.toString(message.getFrom())));
        email.setRecipient(extractEmailAddress(Arrays.toString(message.getRecipients(Message.RecipientType.TO))));

        // CC and BCC
        email.setCc(extractEmailAddress(Arrays.toString(message.getRecipients(Message.RecipientType.CC))));
        //email.setBcc(extractEmailAddress(Arrays.toString(message.getRecipients(Message.RecipientType.BCC))));

        // Other headers
        Enumeration<Header> headers = message.getAllHeaders();
        while (headers.hasMoreElements()) {
            Header header = headers.nextElement();
            // Process each header as needed, for example:
            // if (header.getName().equalsIgnoreCase("X-Some-Custom-Header")) { ... }
        }
    }

    // Converts MimeMessage to Email object
    private Email convertMimeMessageToEmail(MimeMessage message) throws MessagingException, IOException {
        if (message == null) {
            return null;
        }

        Email email = new Email();
        email.setSender(extractEmailAddress(Arrays.toString(message.getFrom())));
        email.setRecipient(extractEmailAddress(Arrays.toString(message.getAllRecipients())));
        email.setSubject(message.getSubject());
        email.setBody(getAppropriateBodyFromMessage(message));

        if (message.getSentDate() != null) {
            email.setTimestamp(LocalDateTime.ofInstant(message.getSentDate().toInstant(), ZoneId.systemDefault()));
        }

        return email;
    }

    // Determines the appropriate body content (HTML, plain text, or multipart)
    private String getAppropriateBodyFromMessage(MimeMessage message) throws MessagingException, IOException {
        if (message.isMimeType("text/html")) {
            return (String) message.getContent();
        } else if (message.isMimeType("text/plain")) {
            return (String) message.getContent();
        } else if (message.isMimeType("multipart/*")) {
            return getContentFromMimeMultipart((MimeMultipart) message.getContent(), message.isMimeType("text/html") ? "text/html" : "text/plain");
        }
        return null;
    }
    private String getTextFromMessage(MimeMessage message) throws MessagingException, IOException {
        return getMessageContent(message, "text/plain");
    }

    private String getHTMLFromMessage(MimeMessage message) throws MessagingException, IOException {
        return getMessageContent(message, "text/html");
    }
    private String getContentFromMimeMultipart(MimeMultipart mimeMultipart, String mimeType) throws MessagingException, IOException {
        StringBuilder result = new   StringBuilder();
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType(mimeType)) {
                result.append((String) bodyPart.getContent());
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result.append(getContentFromMimeMultipart((MimeMultipart) bodyPart.getContent(), mimeType));
            }
        }
        return result.toString();
    }
    private String getMessageContent(MimeMessage message, String mimeType) throws MessagingException, IOException {
        if (message.isMimeType(mimeType)) {
            return (String) message.getContent();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            return getContentFromMimeMultipart(mimeMultipart, mimeType);
        }
        return null;
    }

    // Jsoup.parse is used for HTML to text conversion
    private String convertHtmlToText(String html) {
        return Jsoup.parse(html).text();
    }

    @Override
    public MimeMessage readEmail(String filePath) {
        if (filePath.endsWith(".eml")) {
            return readEmlFile(filePath);
        } else if (filePath.endsWith(".pst")) {
            return readPstFile(filePath); // This could return a list of emails instead
        }
        return null;
    }

    public MimeMessage convertEmail(MimeMessage email, String targetFormat) {
        if ("html".equals(targetFormat)) {
            // Logic to convert email to HTML
        } else if ("text".equals(targetFormat)) {
            // Logic to convert email to plain text
        }
        else if("msg".equals(targetFormat)){
            // Logic to convert email to msg.
            // This could be done by converting the Email object to a MAPIMessage object
            // and then saving the MAPIMessage object to a file.
            // The MAPIMessage class is part of the Apache POI project.
            // See https://poi.apache.org/components/hsmf/how-to.html



        }
        else if("pst".equals(targetFormat)){
            // Logic to convert email to pst
        }
        else if("eml".equals(targetFormat)){
            // Logic to convert email to eml
        }
        // Add more formats as needed
        return email; // Return converted email
    }

    public void saveEmail(MimeMessage email, String savePath) {
        try (OutputStream os = Files.newOutputStream(Paths.get(savePath))) {
            email.writeTo(os);
        } catch (MessagingException | IOException e) {
            e.printStackTrace(); // Consider using a logger instead
        }
    }

    public Object readNonMimeEmail(String filePath) {
        if (filePath.endsWith(".msg")) {
            return readMsgFile(filePath);
        }
        return null;
    }
}

