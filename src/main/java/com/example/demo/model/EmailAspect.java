package com.example.demo.model;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EmailAspect {

    @Autowired
    private JavaMailSender javaMailSender;

    // Define pointcut expressions for the methods in ProductController
    @Before("execution(* com.example.demo.controller.ProductController.*(..))")
    public void sendEmailBeforeMethodExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String to = "fake-email@example.com";
        String subject = "Method Execution Started: " + methodName;
        String text = "The method " + methodName + " in ProductController has started its execution.";
        sendEmail(to, subject, text);
    }

    @After("execution(* com.example.demo.controller.ProductController.*(..))")
    public void sendEmailAfterMethodExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String to = "fake-email@example.com";
        String subject = "Method Execution Completed: " + methodName;
        String text = "The method " + methodName + " in ProductController has completed its execution.";
        sendEmail(to, subject, text);
    }

    // Scheduled email method for demonstration (every 10 minutes)
    @Scheduled(cron = "0 0/3 * * * *")
    public void sendScheduledEmail() {
        String to = "fake-email@example.com";
        String subject = "Scheduled Email: Every 10 minutes";
        String text = "This is a scheduled email sent every 10 minutes from ProductController.";
        System.out.println("Every 10 minutes");
        sendEmail(to, subject, text);
    }

    // New scheduled method to print a message in the console every 1 minute
    @Scheduled(cron = "0 0/1 * * * *")
    public void printConsoleMessage() {
        System.out.println("Schedular Working.");
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
