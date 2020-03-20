package com.gidi.library.controller.v1;


import com.gidi.library.dto.LoginDto;
import com.gidi.library.exception.ResourceNotFoundException;
import com.gidi.library.response.AuthenticationResponse;
import com.gidi.library.security.JwtUtil;
import com.gidi.library.service.LibraryUserDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private LibraryUserDetailService libraryUserDetailService;
    private JwtUtil jwtUtil;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    LibraryUserDetailService libraryUserDetailService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.libraryUserDetailService = libraryUserDetailService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody LoginDto loginDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
                    loginDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ResourceNotFoundException("Incorrect username or password");
        }
        final UserDetails userDetails = libraryUserDetailService.loadUserByUsername(loginDto.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt, "success"));
    }
}
