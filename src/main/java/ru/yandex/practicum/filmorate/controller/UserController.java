package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private Long userId = 1L;
    private final Map<Long, User> users = new HashMap<>();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        User validUser = validateUser(user);
        user.setId(userId);
        users.put(userId++, validUser);
        log.info("Пользователь создан: {}", validUser);
        return validUser;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody User user) {
        User validUser = validateUser(user);

        if (!users.containsKey(validUser.getId())) {
            log.error("Пользователь {} имеет несуществующий id", validUser);
            throw new ValidationException("Пользователь с таким id не существует");
        }
        users.put(validUser.getId(), validUser);
        log.info("Пользователь обновлен: {}", validUser);
        return validUser;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> getAllUsers() {
        return users.values();
    }

    private User validateUser(User user) {
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.error("Пользователь {} имеет некорректный адрес электронной почты", user);
            throw new ValidationException("Некорректный адрес электронной почты");
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.error("Пользователь {} имеет некорректный логин", user);
            throw new ValidationException("Некорректный логин");
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Пользователь {} имеет некорректную дату рождения", user);
            throw new ValidationException("Некорректная дата рождения");
        }
        return user;
    }
}
