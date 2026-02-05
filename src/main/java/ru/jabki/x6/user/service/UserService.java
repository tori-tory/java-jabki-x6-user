package ru.jabki.x6.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.jabki.x6.user.exception.UserException;
import ru.jabki.x6.user.model.User;
import ru.jabki.x6.user.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User create(final User user) {
        validate(user);
        return userRepository.insert(user);
    }

    @Transactional(readOnly = true)
    public User getById(final Long id) {
        return userRepository.getById(id);
    }

    @Transactional
    public User update(final User user) {
        validate(user);
        final User existUser = getById(user.getId());
        existUser.setEmail(user.getEmail());
        existUser.setName(user.getName());
        userRepository.update(existUser);
        return getById(user.getId());
    }

    @Transactional(readOnly = true)
    public boolean existsById(final Long id) {
        return userRepository.existsById(id);
    }

    private void validate(final User user) {
        if (user == null) {
            throw new UserException("Пользователь не задан");
        }
        if (!StringUtils.hasText(user.getName())) {
            throw new UserException("Имя пользователя не может быть пустым");
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new UserException("Email пользователя не может быть пустым");
        }
    }
}