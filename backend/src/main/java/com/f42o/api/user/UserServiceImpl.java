package com.f42o.api.user;

import com.f42o.api.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;



    @Override
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()->new UserNotFoundException("User doesn't exist with id " + id)
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
        //TODO
        User updateUser = userRepository.findById(user.getId()).orElseThrow(
                ()->new UserNotFoundException("User doesn't exist.")
        );


    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()->new UserNotFoundException("User doesn't exist with id " + id)
        );
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public UserResponse getByCredential(String credential) {
        User user = userRepository.findByCredentialNumber(credential).orElseThrow(
                ()-> new UserNotFoundException("User doesn't exist with credential "+ credential));
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
