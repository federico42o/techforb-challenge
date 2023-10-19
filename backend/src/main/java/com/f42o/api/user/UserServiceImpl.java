package com.f42o.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;



    @Override
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()->new RuntimeException("User doesn't exist.")
        );
        return UserResponse.builder()
                .id(user.getId())
                .credentialNumber(user.getCredentialNumber())
                .credentialType(user.getCredentialType())
                .fullName(user.getFullName())
                .build();
    }

    @Override
    public void update(User user) {
        User updateUser = userRepository.findById(user.getId()).orElseThrow(
                ()->new RuntimeException("User doesn't exist.")
        );


    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()->new RuntimeException("User doesn't exist.")
        );
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public UserResponse getByCredential(String credential) {
        User user = userRepository.findByCredentialNumber(credential).orElseThrow(
                ()-> new RuntimeException("User doesn't exist."));
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .credentialType(user.getCredentialType())
                .credentialNumber(user.getCredentialNumber())
                .build();
    }

    @Override
    public Boolean checkByCredential(String credential) {
        return userRepository.findByCredentialNumber(credential).isPresent();
    }
}
