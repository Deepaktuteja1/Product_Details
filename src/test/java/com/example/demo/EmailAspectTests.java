package com.example.demo;
import com.example.demo.utils.EmailAspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

class EmailAspectTests {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailAspect emailAspect;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendEmailAfterMethodExecution() {
        JoinPoint joinPoint = createJoinPointMock("testMethod");

        emailAspect.sendEmailAfterMethodExecution(joinPoint);

        // Verify that sendEmail method was called with the correct parameters
        Mockito.verify(javaMailSender).send(Mockito.any(SimpleMailMessage.class));
    }




    private JoinPoint createJoinPointMock(String methodName) {
        Signature signature = Mockito.mock(Signature.class);
        Mockito.when(signature.getName()).thenReturn(methodName);

        JoinPoint joinPoint = Mockito.mock(JoinPoint.class);
        Mockito.when(joinPoint.getSignature()).thenReturn(signature);

        return joinPoint;
    }
}
