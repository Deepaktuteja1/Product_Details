package com.example.demo;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.example.demo.utils.Schedular;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SchedularTests {

    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setup() {
        // Initialize listAppender
        Logger logger = (Logger) LoggerFactory.getLogger(Schedular.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @Test
    void testSendScheduledEmail() {

        JavaMailSender javaMailSender = mock(JavaMailSender.class);
        Schedular schedular = new Schedular(javaMailSender);


        schedular.sendScheduledEmail();


        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendScheduledEmailWithException() {

        JavaMailSender javaMailSender = mock(JavaMailSender.class);
        doThrow(new RuntimeException("Email sending failed")).when(javaMailSender).send(any(SimpleMailMessage.class));
        Schedular schedular = new Schedular(javaMailSender);


        assertThrows(RuntimeException.class, schedular::sendScheduledEmail);
    }

    @Test
    void testPrintScheduledMessage() {

        Schedular schedular = new Schedular(mock(JavaMailSender.class));
        schedular.printScheduledMessage();


        List<ILoggingEvent> logEvents = listAppender.list;

        assertEquals(1, logEvents.size());
        assertEquals("Scheduler Message", logEvents.get(0).getMessage());
    }
}
