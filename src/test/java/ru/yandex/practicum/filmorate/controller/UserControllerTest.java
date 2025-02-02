package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    private final UserController userController = new UserController();

    @Test
    void userCreateTest() {
        User userCreate = User.builder()
                .login("dolore")
                .name("Nick Name")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();
        assertEquals("Nick Name", userController.createUser(userCreate).getName());
    }

    @Test
    void userCreateWithoutNameTest() {
        User userCreateWithoutName = User.builder()
                .login("dolore")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();
        assertEquals("dolore", userController.createUser(userCreateWithoutName).getName());
    }

    @Test
    void userUpdateTest() {
        User userCreate = User.builder()
                .login("dolore")
                .name("Nick Name")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();
        User userUpdate = userController.createUser(userCreate);
        userUpdate.setName("Nick New Name");
        assertEquals("Nick New Name", userController.updateUser(userUpdate).getName());
    }

    @Test
    void userUpdateUnknownTest() {
        User userUpdateUnknown = User.builder()
                .id(999L)
                .login("dolore")
                .name("Nick Name")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();
        assertThrows(ValidationException.class,
                () -> userController.updateUser(userUpdateUnknown));
    }

    @Test
    void userGetAllTest() {
        Collection<User> userGetAll = userController.getAllUsers();
        assertNotNull(userGetAll);
    }
}