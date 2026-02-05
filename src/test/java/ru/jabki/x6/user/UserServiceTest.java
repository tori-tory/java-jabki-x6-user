package ru.jabki.x6.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabki.x6.user.exception.BadRequestException;
import ru.jabki.x6.user.exception.UserException;
import ru.jabki.x6.user.model.User;
import ru.jabki.x6.user.repository.UserRepository;
import ru.jabki.x6.user.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_valid() {
        final User user = getUser();

        when(userRepository.insert(user)).thenReturn(user);

        User result = userService.create(user);

        assertThat(result).isEqualTo(user);
        verify(userRepository).insert(user);
    }

    @Test
    void createUser_invalidData_nullName_throwUserException() {
        final User user = getUser();
        user.setName(null);

        final UserException exception = assertThrows(
                UserException.class,
                () -> userService.create(user)
        );

        assertEquals(exception.getMessage(), "Имя пользователя не может быть пустым");

        verify(userRepository, never()).insert(any());
    }

    @Test
    void createUser_invalidData_nullEmail_throwUserException() {
        final User user = getUser();
        user.setEmail(null);

        final UserException exception = assertThrows(
                UserException.class,
                () -> userService.create(user)
        );

        assertEquals(exception.getMessage(), "Email пользователя не может быть пустым");

        verify(userRepository, never()).insert(any());
    }

    @Test
    void testExistsById_ThrowException_WhenUserNotFound() {
        final User user = getUser();
        user.setId(100L);
        when(userRepository.existsById(user.getId())).thenReturn(false);

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> userService.existsById(user.getId()),
                String.format("Пользователь с id %d не найден", user.getId())
        );

        assertEquals(String.format("Пользователь с id %d не найден", user.getId()), exception.getMessage());

        verify(userRepository, times(1)).existsById(user.getId());
    }

    @Test
    void testExistsById_NoThrowException_WhenUserFound() {
        final User user = getUser();

        when(userRepository.existsById(user.getId())).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> userService.existsById(user.getId()));

        verify(userRepository, times(1)).existsById(user.getId());
    }

    private User getUser() {
        return User.builder()
                .id(1L)
                .email("почта")
                .name("ФИО")
                .build();
    }
}
