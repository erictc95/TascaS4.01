package cat.itacademy.s04.t01.userapi.repository;

import cat.itacademy.s04.t01.userapi.models.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryUserRepository implements UserRepository  {

    private final List<User> users = new ArrayList<>();

    public void clear() {
        users.clear();
    }

    @Override
    public User save(User user) {
        users.add(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return users.stream().filter(user -> user.id().equals(id)).findFirst();
    }

    @Override
    public List<User> searchByName(String name) {
        return users.stream().filter(user -> user.name().toLowerCase().contains(name.toLowerCase())).toList();
    }

    @Override
    public boolean existsByEmail(String email) {
        return users.stream().anyMatch(user -> user.email().equals(email));
    }
}
