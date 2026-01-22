package ru.jabki.x6.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabki.x6.user.model.User;
import ru.jabki.x6.user.service.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "Пользователи")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Создать пользователя")
    public User create(@RequestBody final User user) {
        return userService.create(user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по id")
    public User getById(@PathVariable("id") final Long id) {
        return userService.getById(id);
    }

    @PatchMapping
    @Operation(summary = "Изменить пользователя")
    public User update(@RequestBody final User user) {
        return userService.update(user);
    }

    @GetMapping("/exists/{id}")
    @Operation(summary = "Проверка, существует ли пользователь")
    public boolean existsById(@PathVariable("id") final Long id) {
        return userService.existsById(id);
        //return String.format("Пользователь с id %d найден", id);
    }
}