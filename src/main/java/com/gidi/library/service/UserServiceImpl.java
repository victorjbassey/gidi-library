package com.gidi.library.service;

import com.gidi.library.dto.UserDto;
import com.gidi.library.exception.PasswordsDoNotMatchException;
import com.gidi.library.exception.UsernameExistsException;
import com.gidi.library.model.Role;
import com.gidi.library.model.User;
import com.gidi.library.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    /**
     * Adds a new user to the library database
     * @param userDto, information about the new user
     * @return the newly added user
     */
    @Override
    public User addUser(UserDto userDto) {
        if (!userDto.getPassword().equals(userDto.getPasswordConfirm())) {
            throw new PasswordsDoNotMatchException("Passwords do not match");
        }
        Optional<User> existingUser = userRepository.findByUsername(userDto.getUsername());
        if (existingUser.isPresent()) {
            throw new UsernameExistsException("Username is already taken. Please use a different one");
        }
        String hashedPassword = new BCryptPasswordEncoder().encode(userDto.getPassword());

        User newUser = new User(userDto.getFirstName(), userDto.getLastName(),
                userDto.getUsername(), hashedPassword);

        Role userRole = roleService.getRole(2);
        newUser.addRole(userRole);
        newUser.setStatus("active");
        logger.info("New user with username - " + newUser.getUsername() + " registered");
        return userRepository.save(newUser);
    }
}
