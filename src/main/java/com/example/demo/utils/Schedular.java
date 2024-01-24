package com.example.demo.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Schedular {

    // Scheduled email method for demonstration (every 10 minutes)
    @Scheduled(cron = "0 0/3 * * * *")
    public void sendScheduledEmail() {
        String to = "fake-email@example.com";
        String subject = "Scheduled Email: Every 10 minutes";
        String text = "This is a scheduled email sent every 1 minutes from ProductController.";
        System.out.println("Every 1 minutes");
        // You can call EmailAspect's sendEmail method here if needed
    }

    // New scheduled method to print a message in the console every 1 minute
    @Scheduled(cron = "0 0/1 * * * *")
    public void printConsoleMessage() {
        System.out.println("Scheduler Working.");
    }
}
