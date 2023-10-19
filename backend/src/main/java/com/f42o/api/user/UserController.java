package com.f42o.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/{credentialNumber}")
    public ResponseEntity<UserResponse> getByCredentialNumber(@PathVariable String credentialNumber){
        return ResponseEntity.status(200).body(userService.getByCredential(credentialNumber));
    }
    @GetMapping("/check/{credentialNumber}")
    public ResponseEntity<Boolean> checkByCredentialNumber(@PathVariable String credentialNumber){
        return ResponseEntity.status(200).body(userService.checkByCredential(credentialNumber));
    }
}
