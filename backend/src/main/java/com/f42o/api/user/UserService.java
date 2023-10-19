package com.f42o.api.user;

import java.util.List;

public interface UserService {

    UserResponse getById(Long id);
    void update(User user);
    void delete(Long id);
    UserResponse getByCredential(String credential);
    Boolean checkByCredential(String credential);
}
