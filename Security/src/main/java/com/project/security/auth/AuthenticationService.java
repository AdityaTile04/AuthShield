package com.project.security.auth;

import com.project.security.config.JwtService;
import com.project.security.repository.UserRepo;
import com.project.security.user.Role;
import com.project.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName( request.getFirstname() )
                .lastName( request.getLastname() )
                .email( request.getEmail() )
                .password( passwordEncoder.encode( request.getPassword() ) )
                .role( Role.USER )
                .build();
        repo.save( user );
        var jwtToken = jwtService.generateToken( user );
        return AuthenticationResponse.builder()
                .token( jwtToken )
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repo.findByEmail( request.getEmail() )
                .orElseThrow();
        var jwtToken = jwtService.generateToken( user );
        return AuthenticationResponse.builder()
                .token( jwtToken )
                .build();
    }
}
