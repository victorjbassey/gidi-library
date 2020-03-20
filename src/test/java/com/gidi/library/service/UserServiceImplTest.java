package com.gidi.library.service;

import com.gidi.library.dto.UserDto;
import com.gidi.library.model.Role;
import com.gidi.library.model.User;
import com.gidi.library.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    RoleService roleService;

    @InjectMocks
    UserServiceImpl userService;

    UserDto userDto;
    User user;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setFirstName("Monica");
        userDto.setLastName("Geller");
        userDto.setUsername("monica");
        userDto.setPassword("password");
        userDto.setPasswordConfirm("password");
        user = new User();
        user.setId(1L);
    }

    @Test
    void addUser() {
        Role userRole = new Role();

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(roleService.getRole(anyInt())).thenReturn(userRole);
        when(userRepository.save(any())).thenReturn(user);

        User newUser = userService.addUser(userDto);
        assertEquals(1L, newUser.getId());

    }
}