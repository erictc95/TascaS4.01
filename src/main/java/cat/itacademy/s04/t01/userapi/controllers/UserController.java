package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.dto.CreateUserRequest;
import cat.itacademy.s04.t01.userapi.exceptions.UserNotFoundException;
import cat.itacademy.s04.t01.userapi.models.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    static final List<User> users = new ArrayList<>();

    @PostMapping("/users")
    public User createUser(@RequestBody CreateUserRequest request) {
        User newUser = new User(UUID.randomUUID(), request.name(), request.email());
        users.add(newUser);
        return newUser;
    }

    @GetMapping("/users/{id}")
    public User findUser(@PathVariable UUID id) {
        return users.stream()
                .filter(user -> user.id().equals(id))
                .findFirst()
                .orElseThrow(UserNotFoundException::new);
    }

    @GetMapping("/users")
    public List<User> findUserByName(@RequestParam(required = false) String name) {
        if (name == null) {
            return users;
        }
        return users.stream()
                .filter(user -> user.name().toLowerCase().contains(name.toLowerCase())).toList();
    }

}
