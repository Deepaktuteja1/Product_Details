package com.example.demo;
import com.example.demo.model.UserInfo;
import com.example.demo.model.UserInfoUserDetails;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.service.UserInfoUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserInfoUserDetailsServiceTests {

    @Mock
    private UserInfoRepository repository;

    @InjectMocks
    private UserInfoUserDetailsService userDetailsService;

    @Test
    void testLoadUserByUsername_UserWithMultipleRoles() {
        // Arrange
        String username = "multiRoleUser";
        UserInfo userInfo = new UserInfo(1, username, "test@example.com", "password", "ROLE_USER,ROLE_ADMIN");
        when(repository.findByName(username)).thenReturn(Optional.of(userInfo));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Assert
        assertNotNull(userDetails);
        assertTrue(userDetails instanceof UserInfoUserDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());

        Set<String> expectedRoles = Set.of("ROLE_USER", "ROLE_ADMIN");
        Set<String> actualRoles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        assertEquals(expectedRoles, actualRoles);

        // Verify
        verify(repository, times(1)).findByName(username);
    }



    @Test
    void testLoadUserByUsername_NullUser() {
        // Arrange
        String username = "nonexistentUser";
        when(repository.findByName(username)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));

        // Verify
        verify(repository, times(1)).findByName(username);
    }




}
