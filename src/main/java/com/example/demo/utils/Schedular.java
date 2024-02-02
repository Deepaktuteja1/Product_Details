package com.example.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Schedular {

    private static final Logger logger = LoggerFactory.getLogger(Schedular.class);

    private final JavaMailSender javaMailSender;

    public Schedular(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    // Scheduled email method for demonstration (every 30 seconds)
    @Scheduled(cron = "0 0 */2 * * *")
    public void sendScheduledEmail() {
        String to = "fake-email@example.com";
        String subject = "Scheduled Email: Every 30 seconds";
        String text = "This is a scheduled email sent every 30 seconds from Schedular.";
        logger.info("Every 30 seconds");

        sendEmail(to, subject, text);
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    @Scheduled(cron = "0 0 */2 * * *")
    public void printScheduledMessage() {
        logger.info("Scheduler Message");
    }
}
