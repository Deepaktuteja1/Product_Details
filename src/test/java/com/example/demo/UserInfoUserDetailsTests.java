package com.example.demo;

import com.example.demo.model.UserInfo;
import com.example.demo.model.UserInfoUserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserInfoUserDetailsTests {

    @Test
    void testConstructorWithValidUserInfo() {
        // Given
        UserInfo userInfo = new UserInfo("JohnDoe", "john@example.com", "password", "ROLE_USER");

        // When
        UserInfoUserDetails userDetails = new UserInfoUserDetails(userInfo);

        // Then
        assertEquals("JohnDoe", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());

        // Convert Collection to List for assertions
        List<GrantedAuthority> authoritiesList = new ArrayList<>(userDetails.getAuthorities());

        assertEquals(1, authoritiesList.size());
        assertEquals("ROLE_USER", authoritiesList.get(0).getAuthority());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
    }






    @Test
    void testConstructorWithMultipleRoles() {
        // Given
        UserInfo userInfo = new UserInfo("JohnDoe", "john@example.com", "password", "ROLE_USER,ROLE_ADMIN");

        // When
        UserInfoUserDetails userDetails = new UserInfoUserDetails(userInfo);

        // Then
        assertEquals("JohnDoe", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());

        // Convert Collection to List for assertions
        List<GrantedAuthority> authoritiesList = new ArrayList<>(userDetails.getAuthorities());

        assertEquals(2, authoritiesList.size());
        assertTrue(authoritiesList.contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(authoritiesList.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
    }

}
