package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public User createUser(User user) {
        return userStorage.addUser(checkUserName(user));
    }

    @Override
    public User updateUser(User user) {
        return userStorage.updateUser(checkUserName(user));
    }

    @Override
    public void addFriend(Long id, Long friendId) {
        userStorage.getUserById(id).getFriends().add(friendId);
        userStorage.getUserById(friendId).getFriends().add(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    @Override
    public User getUserById(Long id) {
        return userStorage.getUserById(id);
    }

    @Override
    public List<User> getFriendsByUserId(Long id) {
        return userStorage.getUserById(id).getFriends().stream()
                .map(userStorage::getUserById)
                .toList();
    }

    @Override
    public void deleteFriend(Long id, Long friendId) {
        userStorage.getUserById(id).getFriends().remove(friendId);
        userStorage.getUserById(friendId).getFriends().remove(id);
    }

    @Override
    public List<User> getCommonUserFriends(Long id, Long friendId) {
        User user1 = userStorage.getUserById(id);
        User user2 = userStorage.getUserById(friendId);
        return user1.getFriends().stream()
                .filter(user1Id -> user2.getFriends().contains(user1Id))
                .map(userStorage::getUserById)
                .toList();
    }

    private User checkUserName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return user;
    }
}
