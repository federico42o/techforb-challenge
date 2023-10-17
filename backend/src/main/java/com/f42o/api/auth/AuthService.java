package com.f42o.api.auth;

import com.f42o.api.jwt.JwtService;
import com.f42o.api.user.Role;
import com.f42o.api.user.User;
import com.f42o.api.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request){
        if(userRepository.findByUsername(request.getCredentialNumber()).isPresent()){
                throw new RuntimeException("User already exist.");
        }
        log.info(passwordEncoder.encode(request.getPassword()));
        log.info(passwordEncoder.encode(""));
        userRepository.save(User.builder()
                .role(Role.USER)
                .isEnabled(true)
                .fullName(request.getFullName())
                .credentialType(request.getCredentialType())
                .credentialNumber(request.getCredentialNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .build());
    }

    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getCredentialNumber(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByUsername(request.getCredentialNumber()).orElseThrow(
                ()-> new UsernameNotFoundException("User doesn't exist.")
        );
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
