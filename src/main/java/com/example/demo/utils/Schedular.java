package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Schedular {

    @Autowired
    private JavaMailSender javaMailSender;

    // Scheduled email method for demonstration (every 30 seconds)
    @Scheduled(cron = "*/30 * * * * *")
    public void sendScheduledEmail() {
        String to = "fake-email@example.com";
        String subject = "Scheduled Email: Every 30 seconds";
        String text = "This is a scheduled email sent every 30 seconds from Schedular.";
        System.out.println("Every 30 seconds");

        sendEmail(to, subject, text);
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void printScheduledMessage() {
        System.out.println("Scheduler Message");
    }
}
