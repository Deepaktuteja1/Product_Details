package com.example.demo;
import com.example.demo.model.UserInfo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserInfoTests {

    @Test
    void testNoArgsConstructor() {
        // Execution
        UserInfo userInfo = new UserInfo();

        // Verification
        assertEquals(0, userInfo.getId());
        assertNull(userInfo.getName());
        assertNull(userInfo.getEmail());
        assertNull(userInfo.getPassword());
        assertNull(userInfo.getRoles());
    }

    @Test
    void testAllArgsConstructor() {
        // Initialization
        int id = 1;
        String name = "Test User";
        String email = "test@example.com";
        String password = "password123";
        String roles = "ROLE_USER";

        // Execution
        UserInfo userInfo = new UserInfo(id, name, email, password, roles);

        // Verification
        assertEquals(id, userInfo.getId());
        assertEquals(name, userInfo.getName());
        assertEquals(email, userInfo.getEmail());
        assertEquals(password, userInfo.getPassword());
        assertEquals(roles, userInfo.getRoles());
    }

    @Test
    void testGettersAndSetters() {
        // Initialization
        UserInfo userInfo = new UserInfo();

        // Set values using setters
        int id = 1;
        String name = "Test User";
        String email = "test@example.com";
        String password = "password123";
        String roles = "ROLE_USER";

        userInfo.setId(id);
        userInfo.setName(name);
        userInfo.setEmail(email);
        userInfo.setPassword(password);
        userInfo.setRoles(roles);

        // Verification
        assertEquals(id, userInfo.getId());
        assertEquals(name, userInfo.getName());
        assertEquals(email, userInfo.getEmail());
        assertEquals(password, userInfo.getPassword());
        assertEquals(roles, userInfo.getRoles());
    }

    @Test
    void testEqualsAndHashCode() {
        // Initialization
        UserInfo user1 = new UserInfo(1, "User1", "user1@example.com", "pass1", "ROLE_USER");
        UserInfo user2 = new UserInfo(1, "User1", "user1@example.com", "pass1", "ROLE_USER");
        UserInfo user3 = new UserInfo(2, "User2", "user2@example.com", "pass2", "ROLE_ADMIN");

        // Verification
        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }

    @Test
    void testToString() {
        // Initialization
        UserInfo userInfo = new UserInfo(1, "Test User", "test@example.com", "password123", "ROLE_USER");

        // Verification
        assertTrue(userInfo.toString().contains("id=1"));
        assertTrue(userInfo.toString().contains("name=Test User"));
        assertTrue(userInfo.toString().contains("email=test@example.com"));
        assertTrue(userInfo.toString().contains("password=password123"));
        assertTrue(userInfo.toString().contains("roles=ROLE_USER"));
    }

    @Test
    void testNoArgsConstructorWithSetter() {
        // Initialization
        UserInfo userInfo = new UserInfo();
        int id = 1;
        String name = "Test User";
        String email = "test@example.com";
        String password = "password123";
        String roles = "ROLE_USER";

        // Set values using setters
        userInfo.setId(id);
        userInfo.setName(name);
        userInfo.setEmail(email);
        userInfo.setPassword(password);
        userInfo.setRoles(roles);

        // Verification
        assertEquals(id, userInfo.getId());
        assertEquals(name, userInfo.getName());
        assertEquals(email, userInfo.getEmail());
        assertEquals(password, userInfo.getPassword());
        assertEquals(roles, userInfo.getRoles());
    }





    @Test
    void testNoArgsConstructorWithEmptyString() {
        // Execution
        UserInfo userInfo = new UserInfo(1, "", "", "", "");

        // Verification
        assertEquals(1, userInfo.getId());
        assertTrue(userInfo.getName().isEmpty());
        assertTrue(userInfo.getEmail().isEmpty());
        assertTrue(userInfo.getPassword().isEmpty());
        assertTrue(userInfo.getRoles().isEmpty());
    }

    @Test
    void testEqualsAndHashCodeWithEmptyString() {
        // Initialization
        UserInfo user1 = new UserInfo(1, "", "", "", "");
        UserInfo user2 = new UserInfo(1, "", "", "", "");

        // Verification
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    // Add more test cases as needed...
}
