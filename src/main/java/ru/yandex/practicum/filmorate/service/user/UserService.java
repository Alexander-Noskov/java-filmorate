package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User updateUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    void addFriend(Long id, Long friendId);

    List<User> getFriendsByUserId(Long id);

    void deleteFriend(Long id, Long friendId);

    List<User> getCommonUserFriends(Long id, Long friendId);
}
