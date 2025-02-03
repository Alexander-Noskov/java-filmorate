package ru.yandex.practicum.filmorate.storage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserStorageTest {
    @Autowired
    private UserStorage userStorage;

    @Test
    void userCreateTest() {
        User userCreate = User.builder()
                .login("dolore")
                .name("Nick Name")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();
        assertEquals("Nick Name", userStorage.addUser(userCreate).getName());
    }

    @Test
    void userUpdateTest() {
        User userCreate = User.builder()
                .login("dolore")
                .name("Nick Name")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();
        User userUpdate = userStorage.addUser(userCreate);
        userUpdate.setName("Nick New Name");
        assertEquals("Nick New Name", userStorage.updateUser(userUpdate).getName());
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
        assertThrows(NotFoundException.class,
                () -> userStorage.updateUser(userUpdateUnknown));
    }

    @Test
    void userGetAllTest() {
        User user = User.builder()
                .login("dolore")
                .name("Nick Name")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();

        User userCreated = userStorage.addUser(user);
        List<User> userGetAll = userStorage.getAllUsers();
        assertTrue(userGetAll.contains(userCreated));
    }

    @Test
    void userGetByIdTest() {
        User user = User.builder()
                .login("dolore")
                .name("Nick Name")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();

        User userCreated = userStorage.addUser(user);
        assertEquals("Nick Name", userStorage.getUserById(userCreated.getId()).getName());
    }

    @Test
    void userGetByIdUnknownTest() {
        assertThrows(NotFoundException.class, () -> userStorage.getUserById(999L));
    }
}