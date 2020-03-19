package com.gidi.library.service;

import com.gidi.library.dto.UserDto;
import com.gidi.library.model.User;

public interface UserService {
    User addUser(UserDto userDto);
}
