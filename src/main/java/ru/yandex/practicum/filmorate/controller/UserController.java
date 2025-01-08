package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

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
    public User createUser(@Valid @RequestBody User user) {
        user.setId(userId);
        User userWithName = checkUserName(user);
        users.put(userId++, userWithName);
        log.info("Пользователь создан: {}", userWithName);
        return userWithName;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@Valid @RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            log.error("Пользователь {} имеет несуществующий id", user);
            throw new ValidationException("Пользователь с таким id не существует");
        }
        User userWithName = checkUserName(user);

        users.put(userWithName.getId(), userWithName);
        log.info("Пользователь обновлен: {}", userWithName);
        return userWithName;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> getAllUsers() {
        return users.values();
    }

    //    Имя для отображения может быть пустым — в таком случае будет использован логин
    private User checkUserName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return user;
    }
}
