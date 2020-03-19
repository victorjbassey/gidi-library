package com.gidi.library.service;

import com.gidi.library.exception.ResourceNotFoundException;
import com.gidi.library.model.LibraryUserDetails;
import com.gidi.library.model.User;
import com.gidi.library.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LibraryUserDetailService implements UserDetailsService {
    private UserRepository userRepository;

    public LibraryUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User theUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Incorrect username or password"));
        return new LibraryUserDetails(theUser);
    }
}
