package com.example.demo;
import com.example.demo.controller.AuthController;
import com.example.demo.model.AuthRequest;
import com.example.demo.model.UserInfo;
import com.example.demo.service.AuthService;
import com.example.demo.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class AuthControllerTests {

    @Mock
    private AuthService authService;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthController authController;

    @Test
    void testAddNewUser() {
        // Mocking
        UserInfo userInfo = new UserInfo();
        when(authService.addUser(userInfo)).thenReturn("User added successfully");

        // Execution
        String result = authController.addNewUser(userInfo);

        // Verification
        assertEquals("User added successfully", result);
        verify(authService, times(1)).addUser(userInfo);
    }

    @Test
    void testAuthenticateAndGetToken_SuccessfulAuthentication() {
        // Mocking
        AuthRequest authRequest = new AuthRequest("username", "password");
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtService.generateToken(authRequest.getUsername())).thenReturn("generatedToken");

        // Execution
        ResponseEntity<String> result = authController.authenticateAndGetToken(authRequest);

        // Verification
        assertEquals("generatedToken", result.getBody());
        assertEquals(200, result.getStatusCodeValue());
        verify(authenticationManager, times(1)).authenticate(any());
        verify(jwtService, times(1)).generateToken(authRequest.getUsername());
    }

    @Test
    void testAuthenticateAndGetToken_UnsuccessfulAuthentication() {
        // Mocking
        AuthRequest authRequest = new AuthRequest("username", "password");
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        // Execution and Verification
        assertThrows(UsernameNotFoundException.class, () -> authController.authenticateAndGetToken(authRequest));
        verify(authenticationManager, times(1)).authenticate(any());
        verify(jwtService, never()).generateToken(anyString());
    }

    @Test
    void testAuthenticateAndGetToken_BadCredentialsException() {
        // Mocking
        AuthRequest authRequest = new AuthRequest("username", "password");
        when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);

        // Execution and Verification
        assertThrows(UsernameNotFoundException.class, () -> authController.authenticateAndGetToken(authRequest));
        verify(authenticationManager, times(1)).authenticate(any());
        verify(jwtService, never()).generateToken(anyString());
    }


}
