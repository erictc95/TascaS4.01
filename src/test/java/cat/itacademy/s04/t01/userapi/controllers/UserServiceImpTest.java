package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.exceptions.EmailAlreadyExistsException;
import cat.itacademy.s04.t01.userapi.models.User;
import cat.itacademy.s04.t01.userapi.repository.UserRepository;
import cat.itacademy.s04.t01.userapi.service.UserServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImp userServiceImp;

    @Test
    void createUser_shouldThrowExceptionWhenEmailAlreadyExists() {
        User userMock = new User(UUID.randomUUID(), "Neymar PSG", "neymar@football.es");
        when(userRepository.existsByEmail(userMock.email())).thenReturn(true);
        assertThrows(EmailAlreadyExistsException.class, () -> userServiceImp.createUser(userMock));
        verify(userRepository, never()).save(userMock);
    }

    @Test
    void createUser_shouldCreateUserSuccessfulIfEmailNotExist() {
        User userMock = new User(UUID.randomUUID(), "Ronaldinho Blaugrana", "ronaldinho@football.es");
        when(userRepository.existsByEmail(userMock.email())).thenReturn(false);
        when(userRepository.save(userMock)).thenReturn(userMock);
        User resultMock = userServiceImp.createUser(userMock);
        assertEquals(userMock, resultMock);
        verify(userRepository, times(1)).save(userMock);
    }
}
