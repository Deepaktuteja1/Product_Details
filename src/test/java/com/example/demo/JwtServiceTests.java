//package com.example.demo;
//import com.example.demo.service.JwtService;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//
//
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//class JwtServiceTests {
//
//    @Mock
//    private Key mockSignKey;
//
//    @InjectMocks
//    private JwtService jwtService;
//
//    @Test
//    void testExtractUsername() {
//        String token = generateToken("testUser");
//        String username = jwtService.extractUsername(token);
//        assertEquals("testUser", username);
//    }
//
//    @Test
//    void testExtractExpiration() {
//        String token = generateToken("testUser");
//        Date expiration = jwtService.extractExpiration(token);
//        assertNotNull(expiration);
//    }
//
//    @Test
//    void testExtractClaim() {
//        String token = generateToken("testUser");
//        String subject = jwtService.extractClaim(token, Claims::getSubject);
//        assertEquals("testUser", subject);
//    }
//
//    @Test
//    void testExtractAllClaims() {
//        String token = generateToken("testUser");
//        Claims claims = jwtService.extractAllClaims(token);
//        assertNotNull(claims);
//        assertEquals("testUser", claims.getSubject());
//    }
//
//
//
//
//    @Test
//    void testValidateToken_ValidToken() {
//        UserDetails userDetails = new User("testUser", "", List.of(() -> "ROLE_USER")); // Provide a non-null and non-empty authority
//        String token = generateToken("testUser");
//
//        assertTrue(jwtService.validateToken(token, userDetails));
//    }
//
//    @Test
//    void testValidateToken_InvalidToken() {
//        UserDetails userDetails = new User("testUser", "", List.of(() -> "ROLE_USER")); // Provide a non-null and non-empty authority
//        String invalidToken = generateToken("otherUser");
//
//        assertFalse(jwtService.validateToken(invalidToken, userDetails));
//    }
//
//    @Test
//    void testGenerateToken() {
//        String token = jwtService.generateToken("testUser");
//        assertNotNull(token);
//    }
//
//    @Test
//    void testCreateToken() {
//        Map<String, Object> claims = new HashMap<>();
//        String token = jwtService.createToken(claims, "testUser");
//        assertNotNull(token);
//    }
//
//    @Test
//    void testGetSignKey() {
//        // Replace "HMACSHA256" with the actual algorithm used in your code
//        when(mockSignKey.getAlgorithm()).thenReturn("HmacSHA384");
//        Key signKey = jwtService.getSignKey();
//        assertNotNull(signKey);
//        assertEquals("HmacSHA384", signKey.getAlgorithm());
//    }
//    private String generateToken(String username) {
//        Map<String, Object> claims = new HashMap<>();
//        return jwtService.createToken(claims, username);
//    }
//
//    private String generateExpiredToken(String username) {
//        Map<String, Object> claims = new HashMap<>();
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(username)
//                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 31))  // Set as expired
//                .setExpiration(new Date(System.currentTimeMillis() - 1000))
//                .signWith(mockSignKey, SignatureAlgorithm.HS256)
//                .compact();
//    }
//}