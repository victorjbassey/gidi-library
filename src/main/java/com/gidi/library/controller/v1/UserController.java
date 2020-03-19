package com.gidi.library.controller.v1;

import com.gidi.library.dto.UserDto;
import com.gidi.library.model.User;
import com.gidi.library.response.ResponseTemplate;
import com.gidi.library.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseTemplate<User> registerUser(@Valid @RequestBody UserDto userDto) {
        User newUser = userService.addUser(userDto);
        return new ResponseTemplate<>(HttpStatus.CREATED.value(), "Successfully registered", newUser);
    }
}
