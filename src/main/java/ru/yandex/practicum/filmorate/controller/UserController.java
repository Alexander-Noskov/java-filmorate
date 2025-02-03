package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getFriendsByUserId(@PathVariable Long id) {
        return userService.getFriendsByUserId(id);
    }

    @PutMapping("/{id}/friends/{friend_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserFriend(@PathVariable Long id, @PathVariable Long friend_id) {
        userService.addFriend(id, friend_id);
    }

    @DeleteMapping("/{id}/friends/{friend_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserFriend(@PathVariable Long id, @PathVariable Long friend_id) {
        userService.deleteFriend(id, friend_id);
    }

    @GetMapping("/{id}/friends/common/{friend_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getCommonUserFriends(@PathVariable Long id, @PathVariable Long friend_id) {
        return userService.getCommonUserFriends(id, friend_id);
    }
}
