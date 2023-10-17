package com.f42o.api.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    @Query("SELECT u FROM User u WHERE u.credentialNumber = ?1")
    Optional<User> findByUsername(String username);


    Optional<User> findByCredentialNumber(String credentialNumber);
}