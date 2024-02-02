package com.example.demo;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertEquals;


 class AuthServiceTests {

    @Mock
    private UserInfoRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void constructor_shouldSetRepositoryAndPasswordEncoder() {

        AuthService authService = new AuthService(repository, passwordEncoder);


        assertEquals(repository, authService.repository);
        assertEquals(passwordEncoder, authService.passwordEncoder);
    }

    @Test
     void constructor_withAutowiredAnnotations_shouldSetRepositoryAndPasswordEncoder() {

        assertEquals(repository, authService.repository);
        assertEquals(passwordEncoder, authService.passwordEncoder);
    }


}
