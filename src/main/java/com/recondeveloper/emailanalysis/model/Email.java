package com.recondeveloper.emailanalysis.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Email {
    private String sender;
    private String recipient;
    private String subject;
    private String body;
    private LocalDateTime timestamp;

    // Constructor
    public Email(String sender, String recipient, String subject, String body, LocalDateTime timestamp) {
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
        this.timestamp = timestamp;
    }

    public Email() {

    }

    // Getters and Setters
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.getSender() != null) {
            sb.append("From: ").append(this.getSender()).append("\n");
        }
        if (this.getRecipient() != null) {
            sb.append("To: ").append(this.getRecipient()).append("\n");
        }
        if (this.getSubject() != null) {
            sb.append("Subject: ").append(this.getSubject()).append("\n");
        }
        if (this.getTimestamp() != null) {
            sb.append("Sent: ").append(this.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append("\n");
        } else {
            sb.append("Sent: [No Timestamp]").append("\n");
        }
        if (this.getBody() != null) {
            sb.append("Body: ").append(this.getBody()).append("\n");
        }
        // Add any other fields with null checks
        return sb.toString();
    }

    public void setCc(String extractEmailAddress) {
    }

// Additional methods like toString() can be added for debugging
}

