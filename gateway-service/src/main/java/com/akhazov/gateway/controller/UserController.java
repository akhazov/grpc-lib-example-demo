package com.akhazov.gateway.controller;

import com.akhazov.gateway.model.CreateUserRequest;
import com.akhazov.gateway.model.CreateUserResponse;
import com.akhazov.gateway.model.UserInfo;
import com.akhazov.gateway.model.UsersResponse;
import com.akhazov.gateway.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponse createUser(@RequestBody CreateUserRequest createRequest) {
        return userService.createUser(createRequest);
    }

    @GetMapping("/{userId}")
    public UserInfo getUserById(@PathVariable("userId") Integer userId) {
        return userService.getUserById(userId);
    }

    @GetMapping
    public UsersResponse getUsers() {
        return userService.getUsers();
    }

    @DeleteMapping
    public void deleteUsers(@RequestParam Integer userId) {
        userService.deleteUserById(userId);
    }

}
