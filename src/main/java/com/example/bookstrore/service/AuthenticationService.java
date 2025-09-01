package com.example.bookstrore.service;

import com.example.bookstrore.model.AuthenticationResponse;
import com.example.bookstrore.model.User;
import com.example.bookstrore.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository _userRepository;
    private final PasswordEncoder _passwordEncoder;
    private final JwtService _jwtService;
    private final AuthenticationManager _authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this._userRepository = userRepository;
        this._passwordEncoder = passwordEncoder;
        this._jwtService = jwtService;
        this._authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(User request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(_passwordEncoder.encode(request.getPassword()));

        user.setRole(request.getRole());

        user = _userRepository.save(user);

        String token = _jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(User request) {
        _authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = _userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = _jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }
}
