package com.example.demo.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EmailAspect {

    private final JavaMailSender javaMailSender;

    public EmailAspect(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @After("execution(* com.example.demo.controller.ProductController.*(..))")
    public void sendEmailAfterMethodExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String to = "fake-email@example.com";
        String subject = methodName;
        String text = "The method " + methodName + " in ProductController has completed its execution.";
        sendEmail(to, subject, text);
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
