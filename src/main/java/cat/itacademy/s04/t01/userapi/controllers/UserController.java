package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.dto.CreateUserRequest;
import cat.itacademy.s04.t01.userapi.models.User;
import cat.itacademy.s04.t01.userapi.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody CreateUserRequest request) {
        User newUser = new User(UUID.randomUUID(), request.name(), request.email());
        return userService.createUser(newUser);
    }

    @GetMapping("/users/{id}")
    public User findUser(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users")
    public List<User> findUserByName(@RequestParam(required = false) String name) {
        if (name == null) {
            return userService.getAllUsers();
        }
        return userService.searchUsersByName(name);
    }

}
